package ma.ac.ensa.ebankingapi.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class AccountNumberGenerator implements IdentifierGenerator {

    /**
	 * CodeBank: This Code is Unique For Each Bank
	 *
	 * @var String
	 */
    private final static String codeBank = "43947";

    @Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return AccountNumberGenerator.generateAccountNumber();
	}

	public static String generateAccountNumber() {
		/*
			France et Monaco (23 caractères) Format : BBBBBGGGGGCCCCCCCCCCCKK
			B = code banque (5 chiffres),
			G = code guichet (5 chiffres),
			C = numéro de compte (11 chiffres et/ou lettres),
			K = clé RIB (2 chiffres entre 01 et 97)
		*/
		String accountNumber = "";

		accountNumber += String.join("", codeBank);
		accountNumber += String.join("", generateATMCode());
		accountNumber += String.join("", generateUserAccountNumber());
		accountNumber += String.join("", generateRIBKey());

		return accountNumber;
	}

	/**
	 * ATM Code
	 *
	 * @return String
	 */
	private static String generateATMCode() {
		int length = 5;
		String[] atmCode = new String[length];

		for (int i = 0; i<length; i++) {
			atmCode[i] = String.valueOf(new Random().nextInt(10));
		}

		return String.join("", atmCode);
	}

	/**
	 * RIB Key : Bank Account Identity Key
	 *
	 * @return String
	 */
	private static String generateRIBKey() {
		String cleRIB = "";

		cleRIB += String.valueOf(new Random().nextInt(10));
		cleRIB += String.valueOf(new Random().nextInt(7)+1);

		return cleRIB;
	}

	/**
	 * User Account Number
	 *
	 * @return String[]
	 */
	private static String generateUserAccountNumber() {
		int length = 11;
		String[] accountNumber = new String[length];

		for (int i = 0; i<11; i++) {
			accountNumber[i] = String.valueOf(new Random().nextInt(10));
		}

		return String.join("", accountNumber);
	}
}
