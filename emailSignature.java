import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class emailSignature{

    public static byte[] SignEmail(PrivateKey PK,byte[] byteMessage)throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature _signature = Signature.getInstance("SHA256withECDSA");
        _signature.initSign(PK);
        _signature.update(byteMessage);
        return _signature.sign();
    }

}