package br.com.zup.bancodigital.core.security;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.passay.WhitespaceRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordValidatorConfig {

	@Bean
	public PasswordValidator passwordValidator() {
		// length 8 characters
		LengthRule lengthRule = new LengthRule(8);

		// at least one upper-case character
		CharacterRule characterRuleUpper = new CharacterRule(EnglishCharacterData.UpperCase, 1);

		// at least one lower-case character
		CharacterRule characterRuleLower = new CharacterRule(EnglishCharacterData.LowerCase, 1);

		// at least one digit character
		CharacterRule characterRuleDigit = new CharacterRule(EnglishCharacterData.Digit, 1);

		// at least one symbol (special character)
		CharacterRule characterRuleSpecial = new CharacterRule(EnglishCharacterData.Special, 1);

		/*
		 * // define some illegal sequences that will fail when >= 5 chars long //
		 * alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
		 * // the false parameter indicates that wrapped sequences are allowed; e.g.
		 * 'xyzabc' new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
		 * new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false), new
		 * IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
		 */

		// no whitespace
		WhitespaceRule whitespaceRule = new WhitespaceRule();

		return new PasswordValidator(lengthRule, characterRuleUpper, characterRuleLower, characterRuleDigit,
				characterRuleSpecial, whitespaceRule);
	}

}
