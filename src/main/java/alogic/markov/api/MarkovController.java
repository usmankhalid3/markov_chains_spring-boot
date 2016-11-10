package alogic.markov.api;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import com.google.common.collect.Lists;

import alogic.markov.core.MarkovText;
import alogic.markov.core.Prefix;
import alogic.markov.core.exceptions.InvalidOrderException;

@Controller
@EnableAutoConfiguration
public class MarkovController {

	@GetMapping("/")
	private String markovText() {
		return "markov";
	}
	
	@PostMapping("/")
	private String markovText(@RequestParam("order") int order,
								@RequestParam("maxwords") int maxWords,
								@RequestParam("file") MultipartFile file,
								@RequestParam("prefix") String prefixStr,
								Model model) {
		String corpus = readFileData(file);

		if (!StringUtils.isEmpty(corpus)) {
			try {
				Prefix prefix = getPrefix(prefixStr);
				if (prefix != null && prefix.size() != order) {
					model.addAttribute("error", "Prefix length should be same as Order");
				}
				else {
					MarkovText markov = new MarkovText(order);
					markov.addWords(extractWords(corpus));
					String output = markov.generateText(maxWords, prefix);
					model.addAttribute("output", output);
				}
			}
			catch(InvalidOrderException e) {
				model.addAttribute("error", e.getMessage());
			}
			
		} else {
			model.addAttribute("error", "Invalid input or no file selected");
		}
				
		return "markov";

	}
	
	private Prefix getPrefix(String prefixStr) {
		if (StringUtils.isEmpty(prefixStr)) {
			return null;
		}
		String[] prefixes = prefixStr.split("\\s+");
		return new Prefix(prefixes);
	}
	
	private String readFileData(MultipartFile file) {
		 if (!file.isEmpty()) {
		        try {
		            byte[] bytes = file.getBytes();
		            return new String(bytes);
		        }
		        catch(Exception e) {
		        	throw new RuntimeException(e);
		        }
		 }
		 return null;
	}
	
	private List<String> extractWords(String corpus) {
		String[] lines = corpus.split("\\r?\\n");
		if (ArrayUtils.isEmpty(lines)) {
			return Lists.newArrayList();
		}
		List<String> result = Lists.newArrayList();
		for (int i = 0; i < lines.length; i++) {
			String[] words = lines[i].split("\\s+");
			for (int j = 0; j < words.length; j++) {
				result.add(words[j]);
			}
		}
		return result;
	}
}
