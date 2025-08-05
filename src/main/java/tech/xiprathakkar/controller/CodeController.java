package tech.xiprathakkar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tech.xiprathakkar.beans.Code;
import tech.xiprathakkar.beans.Translate;

@Controller
public class CodeController {

	List<Code>CodeList=new CopyOnWriteArrayList<Code>();
	List<Translate>TranslatedList=new CopyOnWriteArrayList<Translate>();
	
	@GetMapping("/morse-code")
	public String morseCode(Model model, @ModelAttribute Code code) {
		model.addAttribute("code", new Code());
		model.addAttribute("CodeList", CodeList);
		model.addAttribute("TranslatedList", TranslatedList);
		return "morse-code";
	}
	
	private static final HashMap<Character, String> textToMorseMap = new HashMap<>();
	
    static {
    	textToMorseMap.put('A', ".-");
        textToMorseMap.put('B', "-...");
        textToMorseMap.put('C', "-.-.");
        textToMorseMap.put('D', "-..");
        textToMorseMap.put('E', ".");
        textToMorseMap.put('F', "..-.");
        textToMorseMap.put('G', "--.");
        textToMorseMap.put('H', "....");
        textToMorseMap.put('I', "..");
        textToMorseMap.put('J', ".---");
        textToMorseMap.put('K', "-.-");
        textToMorseMap.put('L', ".-..");
        textToMorseMap.put('M', "--");
        textToMorseMap.put('N', "-.");
        textToMorseMap.put('O', "---");
        textToMorseMap.put('P', ".--.");
        textToMorseMap.put('Q', "--.-");
        textToMorseMap.put('R', ".-.");
        textToMorseMap.put('S', "...");
        textToMorseMap.put('T', "-");
        textToMorseMap.put('U', "..-");
        textToMorseMap.put('V', "...-");
        textToMorseMap.put('W', ".--");
        textToMorseMap.put('X', "-..-");
        textToMorseMap.put('Y', "-.--");
        textToMorseMap.put('Z', "--..");
        textToMorseMap.put('1', ".----");
        textToMorseMap.put('2', "..---");
        textToMorseMap.put('3', "...--");
        textToMorseMap.put('4', "....-");
        textToMorseMap.put('5', ".....");
        textToMorseMap.put('6', "-....");
        textToMorseMap.put('7', "--...");
        textToMorseMap.put('8', "---..");
        textToMorseMap.put('9', "----.");
        textToMorseMap.put('0', "-----");
    }
    
    private String toMorse(String input) {
        if (input == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char ch : input.toUpperCase().toCharArray()) {
            if (ch == ' ') {
                sb.append(" / ");
                continue;
            }
            String morse = textToMorseMap.get(ch);
            if (morse != null) {
                sb.append(morse).append(' ');
            } else {
                sb.append("? ");
            }
        }
        return sb.toString().trim();
    }
    
	@PostMapping("/translate")
	public String translate(Model model, @ModelAttribute Code code) {
	    CodeList.add(code);
	    
	    String output = toMorse(code.getCode());
	    
	    TranslatedList.add(Translate.builder().translation(output).build());
	    
	    model.addAttribute("code", new Code());
		model.addAttribute("CodeList", CodeList);
		model.addAttribute("TranslatedList", TranslatedList);
		return "morse-code";
	}
}
