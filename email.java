import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.PrivateKey;
import java.security.Signature;

public class email{

    String SenderAddress;
    String ReceiverAddress;
    String content;

    public email( ){
        this.SenderAddress = null;
        this.ReceiverAddress = null;
        this.content = null;
    }

    public void setSender(String Address){
        this.SenderAddress = Address;
    }


}