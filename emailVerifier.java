import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class emailVerifier{

    public static byte[] SignEmail(PrivateKey PK,email Email)throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature _signature = Signature.getInstance("SHA256withECDSA");
        String email_content = Email.SenderAddress + Email.SenderAddress + Email.content;
        _signature.initSign(PK);
        _signature.update(email_content.getBytes());
        return _signature.sign();
    }

}