import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public class user {
    String name;
    String EmailAddress;
    KeyPair keyPair;

    public user(String _name,String _email,KeyPairGenerator KPG) throws NoSuchFieldException, InvalidAlgorithmParameterException{
        this.name= _name;
        this.EmailAddress = _email;
        KPG.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
        this.keyPair = KPG.generateKeyPair();
    }

    public PublicKey getPublicKey(){
        return this.keyPair.getPublic();
    }

    public PrivateKey getPrivateKey(){
        return this.keyPair.getPrivate();
    }
}
