package unigearrent.unigearrentjava.services.miscellaneous;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@Getter
@Setter
public class RSAKeys {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeys()
    {
        KeyPair pair = KeyGenerator.GenerateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }
}
