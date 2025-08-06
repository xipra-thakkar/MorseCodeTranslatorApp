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

	List<Code>eToMCodeList=new CopyOnWriteArrayList<Code>();
	List<Translate>eToMTranslatedList=new CopyOnWriteArrayList<Translate>();
	
	List<Code>mToECodeList=new CopyOnWriteArrayList<Code>();
	List<Translate>mToETranslatedList=new CopyOnWriteArrayList<Translate>();
	
	@GetMapping("/morse-code")
	public String morseCode(Model model, @ModelAttribute Code code) {
		model.addAttribute("code", new Code());
		model.addAttribute("CodeList", eToMCodeList);
		model.addAttribute("TranslatedList", eToMTranslatedList);
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
	    eToMCodeList.add(code);
	    
	    String output = toMorse(code.getCode());
	    
	    eToMTranslatedList.add(Translate.builder().translation(output).build());
	    
	    model.addAttribute("code", new Code());
		model.addAttribute("CodeList", eToMCodeList);
		model.addAttribute("TranslatedList", eToMTranslatedList);
		return "morse-code";
	}
	
	@GetMapping("/morse-to-text")
    public String morseToText(Model model) {
        model.addAttribute("code", new Code());
        model.addAttribute("CodeList", mToECodeList);
        model.addAttribute("TranslatedList", mToETranslatedList);
        return "morse-to-text";
    }
	
	private static final HashMap<String, Character> morseToTextMap = new HashMap<>();
	
	static {
		morseToTextMap.put(".-", 'A');
        morseToTextMap.put("-...", 'B');
        morseToTextMap.put("-.-.", 'C');
        morseToTextMap.put("-..", 'D');
        morseToTextMap.put(".", 'E');
        morseToTextMap.put("..-.", 'F');
        morseToTextMap.put("--.", 'G');
        morseToTextMap.put("....", 'H');
        morseToTextMap.put("..", 'I');
        morseToTextMap.put(".---", 'J');
        morseToTextMap.put("-.-", 'K');
        morseToTextMap.put(".-..", 'L');
        morseToTextMap.put("--", 'M');
        morseToTextMap.put("-.", 'N');
        morseToTextMap.put("---", 'O');
        morseToTextMap.put(".--.", 'P');
        morseToTextMap.put("--.-", 'Q');
        morseToTextMap.put(".-.", 'R');
        morseToTextMap.put("...", 'S');
        morseToTextMap.put("-", 'T');
        morseToTextMap.put("..-", 'U');
        morseToTextMap.put("...-", 'V');
        morseToTextMap.put(".--", 'W');
        morseToTextMap.put("-..-", 'X');
        morseToTextMap.put("-.--", 'Y');
        morseToTextMap.put("--..", 'Z');
        morseToTextMap.put(".----", '1');
        morseToTextMap.put("..---", '2');
        morseToTextMap.put("...--", '3');
        morseToTextMap.put("....-", '4');
        morseToTextMap.put(".....", '5');
        morseToTextMap.put("-....", '6');
        morseToTextMap.put("--...", '7');
        morseToTextMap.put("---..", '8');
        morseToTextMap.put("----.", '9');
        morseToTextMap.put("-----", '0');
        for (var entry : textToMorseMap.entrySet()) {
            morseToTextMap.put(entry.getValue(), entry.getKey());
        }
	}
	
	private String toText(String morseCode) {
        if (morseCode == null) return "";
        StringBuilder sb = new StringBuilder();
        String[] words = morseCode.trim().replaceAll("\\s+", " ").split("\\s*/\\s*"); // tolerate spaces around /
        for (String word : words) {
            if (word.isBlank()) continue;
            for (String letter : word.trim().split(" ")) {
                if (letter.isBlank()) continue;
                Character c = morseToTextMap.get(letter);
                if (c != null) sb.append(c);
            }
            sb.append(' ');
        }
        return sb.toString().trim();
    }
	
	@PostMapping("/morse-to-text")
    public String morseToTextTranslate(Model model, @ModelAttribute Code code) {
        mToECodeList.add(code);
        String output = toText(code.getCode());
        mToETranslatedList.add(Translate.builder().translation(output).build());

        model.addAttribute("code", new Code());
        model.addAttribute("CodeList", mToECodeList);
        model.addAttribute("TranslatedList", mToETranslatedList);
        return "morse-to-text";
    }

}
