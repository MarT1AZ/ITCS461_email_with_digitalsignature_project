import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.security.PrivateKey;
import java.security.Signature;

public class email{

    String SenderAddress;
    String ReceiverAddress;
    ArrayList<String> content;

    public email( ){
        this.SenderAddress = null;
        this.ReceiverAddress = null;
        this.content = new ArrayList<String>();
    }

    public void setSender(String Address){
        this.SenderAddress = Address;
    }

    public String toString(){
        String stringEmail = "";
        stringEmail += this.SenderAddress + "\n";
        stringEmail += this.ReceiverAddress + "\n";
        for(int lidx = 0;lidx < this.content.size();lidx++){
            stringEmail = stringEmail + this.content.get(lidx) + "\n";
        }
        return stringEmail;
    }

    public byte[] toByte(){
        return this.toString().getBytes();
    }

    


}