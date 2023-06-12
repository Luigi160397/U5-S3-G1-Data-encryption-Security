package epicode.u5s3g1.entities;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import jakarta.persistence.AttributeConverter;

public class CodiceEdificioConverter implements AttributeConverter<String, String> {

	private final static String ALGORITHM = "AES/ECB/PKCS5Padding";

	private final static String secret = "mZq3t6w9zxCxFxgh";

	@Override
	public String convertToDatabaseColumn(String codice) {
		try {
			Key key = new SecretKeySpec(secret.getBytes(), "AES");
			Cipher c = Cipher.getInstance(ALGORITHM);

			c.init(Cipher.ENCRYPT_MODE, key);

			return Base64.getEncoder().encodeToString(c.doFinal(codice.getBytes()));

		} catch (RuntimeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {

			throw new RuntimeException();
		}
	}

	@Override
	public String convertToEntityAttribute(String dbCodice) {
		Key key = new SecretKeySpec(secret.getBytes(), "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key);

			return new String(c.doFinal(Base64.getDecoder().decode(dbCodice)));

		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException();
		}

	}

}
