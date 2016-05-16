package com.masato.ka.soracom.security;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;
import java.util.Base64;
import java.util.Base64.Decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SoracomSigningKeyResolver implements SigningKeyResolver {
	@Value(value = "soracom.certfile")
	private final String publicCert = "v1-f2fea060b93f510bfb722f2cd4b3774e-x509.pem";
	
	@Override
	public Key resolveSigningKey(JwsHeader jwsHeader, String plaintxt) {
		PublicKey pubKey=null;
		try {
			pubKey = KeyFactory.getInstance("RSA")
			        .generatePublic(new X509EncodedKeySpec(readKey(publicCert)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return pubKey;
	}

	@Override
	public Key resolveSigningKey(JwsHeader jwsHeader, Claims claim) {
		PublicKey pubKey=null;
		try {
			pubKey = KeyFactory.getInstance("RSA")
			        .generatePublic(new X509EncodedKeySpec(readKey(publicCert)));
			
		} catch (Exception e){
			
		}
				
		return pubKey;
	}
	
	private static byte[] readKey(final String fileName) throws Exception {
		//http://hondou.homedns.org/pukiwiki/pukiwiki.php?JavaSE%20RSA%B0%C5%B9%E6%20Java8
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream keyStream = loader.getResourceAsStream(fileName);
        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(keyStream))) {
            String line;
            StringBuilder sb = new StringBuilder();
            boolean isContents = false;
 
            while ((line = br.readLine()) != null) {
                if (line.matches("[-]+BEGIN[ A-Z]+[-]+")) {
                    isContents = true;
                } else if (line.matches("[-]+END[ A-Z]+[-]+")) {
                    break;
                } else if (isContents) {
                    sb.append(line);
                }
            }
 
            return Base64.getDecoder().decode(sb.toString());
        } catch (FileNotFoundException e) {
            throw new Exception("File not found.", e);
        } catch (IOException e) {
            throw new Exception("can't read the PEM file.", e);
        }
    }
	
	
}
