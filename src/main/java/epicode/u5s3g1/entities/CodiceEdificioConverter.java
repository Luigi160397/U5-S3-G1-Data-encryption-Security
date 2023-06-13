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

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.AttributeConverter;

public class CodiceEdificioConverter implements AttributeConverter<String, String> {

	private static String ALGORITMO;
	private static String SEGRETO;

	@Value("${spring.application.segreto}")
	public void setSEGRETO(String segreto) {
		SEGRETO = segreto;
	}

	@Value("${spring.application.algoritmo}")
	public void setALGORITMO(String algoritmo) {
		ALGORITMO = algoritmo;
	}

	@Override
	public String convertToDatabaseColumn(String codice) {
		try {
			Key key = new SecretKeySpec(SEGRETO.getBytes(), "AES");
			Cipher c = Cipher.getInstance(ALGORITMO);

			c.init(Cipher.ENCRYPT_MODE, key);

			return Base64.getEncoder().encodeToString(c.doFinal(codice.getBytes()));

		} catch (RuntimeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {

			throw new RuntimeException();
		}
	}

	@Override
	public String convertToEntityAttribute(String dbCodice) {
		Key key = new SecretKeySpec(SEGRETO.getBytes(), "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITMO);
			c.init(Cipher.DECRYPT_MODE, key);

			return new String(c.doFinal(Base64.getDecoder().decode(dbCodice)));

		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException();
		}

	}

}
