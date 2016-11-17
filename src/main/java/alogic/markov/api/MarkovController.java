package alogic.markov.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;

import alogic.markov.core.MarkovText;
import alogic.markov.core.Prefix;
import alogic.markov.core.exceptions.InvalidOrderException;
import alogic.markov.util.FileUtil;
import alogic.markov.util.TextUtil;

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
		String corpus = FileUtil.readFile(file);

		if (!Strings.isNullOrEmpty(corpus)) {
			try {
				Prefix prefix = Prefix.fromString(prefixStr, "\\s+");
				if (prefix != null && prefix.size() != order) {
					model.addAttribute("error", "Prefix length should be same as Order");
				}
				else {
					MarkovText markov = new MarkovText(order);
					markov.addWords(TextUtil.extractWords(corpus));
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
}
