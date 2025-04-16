import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.MessageDigest;
import javax.crypto.Cipher;




public class main_ {

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    public static void hexadecimalPrint(byte[] bytes){
        System.out.print("hexadecimal view : ");
        for(int bidx = 0;bidx < bytes.length;bidx++){
            System.out.print(String.format("%02X", bytes[bidx]));
        }
        System.out.print("\n");
    } 

    public static boolean verify(byte[] hash1,byte[] hash2){
        for(int bidx = 0;bidx < hash1.length;bidx++){
            if(hash1[bidx] != hash2[bidx]){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        // System.out.print("\033[H\033[2J");  
        // System.out.flush();  
        user Sender = null;
        user Reciever = null;
        email draftEmail = new email();

        ///////////////////////////////////////////////
        // for simulating sending
        MessageDigest senderMD = null;
        Cipher senderCipherRSA;
        byte[] sentEncryptedHash;
        String stringMessage;
        byte[] sentByteMessage;
        byte[] hashValue;

        //////////////// attack


        
        ///////////////// Recieved
        
        Cipher recieverCipherRSA;
        MessageDigest recieverMD;
        byte[] recievedEncryptedHash;
        byte[] receivedByteMessage;
        byte[] decryptedHashValue;
        byte[] hashValueOfReievedMessage;

        // String stringMessageHexadecimal = "";
        // byte[] signedSignature = null;
        // String stringSignature = "";

        // byte[] ReceivedByteMessage = null;
        // Byte[] ReceivedSignature;
        // String stringRecievedMessage;
        // String stringRecievedMessageHexadecimal;
        // String stringDecodedSignature;

        ///////////////////////////////////////////////


        // define array of user
        // want 3 users
        // 3 keypairs
        ArrayList<user> users = new ArrayList<user>();
        ArrayList<String> publicEmailList = new ArrayList<String>();
        publicEmailList.add("alice@gmail.com");
        publicEmailList.add("bob@gmail.com");
        publicEmailList.add("jack@gmail.com");

        try{
            users.add(new user("alice",publicEmailList.get(0),KeyPairGenerator.getInstance("RSA")));
            users.add(new user("bob",publicEmailList.get(1),KeyPairGenerator.getInstance("RSA")));
            users.add(new user("jack",publicEmailList.get(2),KeyPairGenerator.getInstance("RSA")));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        ArrayList<PublicKey> PublicKeyList = new ArrayList<PublicKey>();
        for(int uidx = 0;uidx < users.size();uidx++){
            PublicKeyList.add(users.get(uidx).getPublicKey());
        }


        Scanner sc = new Scanner(System.in);

        boolean exit_program = false;
        
        enum FUNCTION {
            EXIT,
            SELECT_FUNCTION,
            SELECT_SENDER,
            SELECT_RECIEVER,  
            VIEW_EMAIL,
            COMPOSE_EMAIL,
            SIMULATE_SENDING,
        }
        int function_value = 0;
        int selected_userID = 0;
        int line_count = 1;
        String content_line = "";

  

        // EXIT = 0
        // SELECT_FUNCTION = 1
        // SELECT_SENDER = 2
        // SELECT_RECIEVER = 3
        // VIEW_EMAIL = 4
        // COMPOSE_EMAIL = 5
        // SIMULATE_SENDING = 6

        FUNCTION function_mode = FUNCTION.SELECT_FUNCTION;
        
        while(!exit_program){

            if(function_mode == FUNCTION.SELECT_FUNCTION){////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("current FUNCTION : " + String.valueOf(function_mode));
                System.out.println("Please select function mode");
                for(int fidx = 0; fidx < FUNCTION.values().length;fidx++){
                    System.out.printf("function id %d  mode = %s \n",fidx,FUNCTION.values()[fidx].toString());
                }
                System.out.print("your input : ");

                // function_value = sc.nextInt();

                function_value = -1;
                while(function_value == -1){
                    try{
                        function_value = Integer.parseInt(sc.nextLine());
                    }catch(Exception e){
                        System.out.println("given command value is not an integer please try again");
                        continue;
                    }
                    if(function_value < 0 || function_value > 6){
                        System.out.println("given command value is out of bound, please try again!");
                        function_value = -1;
                    }
                }

                // while(function_value < 0 || function_value > 4){
                //     System.out.println("try again!");
                //     function_value = sc.nextInt();
                // }

                function_mode = FUNCTION.values()[function_value];
                clearScreen();
                

            }

            
            if(function_mode == FUNCTION.SELECT_SENDER){////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("current FUNTION : " + String.valueOf(function_mode));
                System.out.println("Avialable user to select as a SENDER\n");
                for(int uidx = 0;uidx < users.size();uidx++){
                    System.out.println("user ID : " + (uidx + 1) + " user name : " + users.get(uidx).name + " email : " + users.get(uidx).EmailAddress);
                }
                System.out.println("\nPlease select user as a SENDER by user ID");

                selected_userID = -1;
                while(selected_userID == -1){
                    try{
                        System.out.print("selecting with user ID : ");
                        selected_userID = Integer.parseInt(sc.nextLine());
                    }catch(Exception e){
                        System.out.println("given user ID is not an integer please try again");
                        continue;
                    }
                    if(selected_userID <= 0 || selected_userID > users.size()){
                        System.out.println("given user ID is out of bound, please try again!");
                        selected_userID = -1;
                    }
                }
                
                // selected_userID = Integer.parseInt(sc.nextLine());
                // while(selected_userID < 1 || selected_userID > users.size()){
                //     System.out.println("invalid user ID please try again");
                //     selected_userID = sc.nextInt();
                // }
                selected_userID = selected_userID - 1; // transform into users list index
                Sender = users.get(selected_userID);
                draftEmail.SenderAddress = Sender.EmailAddress;
                System.out.println("SENDER is user ID : " + (selected_userID + 1) + " user name : " + Sender.name + " email : " + Sender.EmailAddress);
                // select user
                function_mode = FUNCTION.SELECT_FUNCTION;
                clearScreen();

            }
            else if(function_mode == FUNCTION.SELECT_RECIEVER){////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                
                
                
                
                System.out.println("current FUNTION : " + String.valueOf(function_mode));
                System.out.println("Avialable user to select as a RECIEVER\n");
                for(int uidx = 0;uidx < users.size();uidx++){
                    System.out.println("user ID : " + (uidx + 1) + " user name : " + users.get(uidx).name + " email : " + users.get(uidx).EmailAddress);
                }
                System.out.println("\nPlease select user as a RECIEVER by user ID");

                selected_userID = -1;
                while(selected_userID == -1){
                    try{
                        System.out.print("selecting with user ID : ");
                        selected_userID = Integer.parseInt(sc.nextLine());
                    }catch(Exception e){
                        System.out.println("given user ID is not an integer please try again");
                        continue;
                    }
                    if(selected_userID <= 0 || selected_userID > users.size()){
                        System.out.println("given user ID is out of bound, please try again!");
                        selected_userID = -1;
                    }
                }
                // selected_userID = sc.nextInt();
                // while(selected_userID < 1 || selected_userID > users.size()){
                //     System.out.println("invalid user ID please try again");
                //     selected_userID = sc.nextInt();
                // }
                selected_userID = selected_userID - 1; // transform into users list index
                Reciever = users.get(selected_userID);
                draftEmail.ReceiverAddress = Reciever.EmailAddress;
                System.out.println("RECIEVER is user ID : " + (selected_userID + 1) + " user name : " + Reciever.name + " email : " + Reciever.EmailAddress);
                // select user
                function_mode = FUNCTION.SELECT_FUNCTION;
                clearScreen();
            }
            else if(function_mode == FUNCTION.COMPOSE_EMAIL){////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("current FUNTION : " + String.valueOf(function_mode));
                System.out.println("Start compose email by typing each line");
                System.out.println("type ESC to escape");
                System.out.println("press ENTER to new line");
                line_count = 0;
                do{
                    System.out.println("type 'ESC' case sensitive to escape\n");
                    for(int lidx = 0; lidx < line_count;lidx++){
                        System.out.printf("Line %d : %s\n",lidx,draftEmail.content.get(lidx));
                    }
                    System.out.print("Line " + line_count + " : ");
                    content_line = sc.nextLine();
                    line_count = line_count + 1;
                    if(content_line.equals("ESC")){
                        break;
                    }
                    draftEmail.content.add(content_line);
                    clearScreen();
                }while(!content_line.equals("ESC"));
                System.out.println(draftEmail.content);

                function_mode = FUNCTION.VIEW_EMAIL;
                clearScreen();
            }
            else if(function_mode == FUNCTION.VIEW_EMAIL){////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("current FUNCTION : " + String.valueOf(function_mode));
                System.out.print("\n");
                if(Sender == null){
                    System.out.println("Sender address {HAS NOT BEEN SELECTED}");
                }else{
                    System.out.printf("Sender address is %s \n",Sender.EmailAddress);
                }
                if(Reciever == null){
                    System.out.println("Receiver address {HAS NOT BEEN SELECTED}");
                }else{
                    System.out.printf("Receiver address is %s \n",Reciever.EmailAddress);
                }
                System.out.print("\n");
                if(draftEmail.content.size() == 0){
                    System.out.println("Email has not been drafted");
                }
                for(int lidx = 0;lidx < draftEmail.content.size();lidx++){
                    System.out.printf("line %d : %s \n",lidx,draftEmail.content.get(lidx));
                }

                System.out.print("\n");
                System.out.println("Press any keys to return ");
                sc.nextLine();
                function_mode = FUNCTION.SELECT_FUNCTION;
                clearScreen();

            }
            else if(function_mode == FUNCTION.SIMULATE_SENDING){ ////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                
                clearScreen();
                try{
                    senderMD = MessageDigest.getInstance("SHA256");
                    senderCipherRSA = Cipher.getInstance("RSA");

                    stringMessage = draftEmail.toString();
                    sentByteMessage = stringMessage.getBytes();
                    hashValue = senderMD.digest(sentByteMessage);
                    
                    senderCipherRSA.init(Cipher.ENCRYPT_MODE, Sender.getPrivateKey());
                    sentEncryptedHash = senderCipherRSA.doFinal(draftEmail.toByte());

                    System.out.println("email content in hexadecimal\n");
                    hexadecimalPrint(sentByteMessage);
                    System.out.println("\nHash of the email in hexadecimal\n");
                    hexadecimalPrint(hashValue);
                    System.out.println("\nEncrypted hash in hexadecimal using RSA\n");
                    hexadecimalPrint(sentEncryptedHash);

                    //////////////////// SEND MESSAGE PRE ATTACK
                    System.out.println("\n\nSENDING MESSAGE\n\n");
                    //////////////////// SEND MESSAGE PRE ATTACK
                    

                    /////////// recieving end
                    recievedEncryptedHash = sentEncryptedHash;
                    receivedByteMessage = sentByteMessage;

                    recieverMD = MessageDigest.getInstance("SHA256");
                    recieverCipherRSA = Cipher.getInstance("RSA");
                    recieverCipherRSA.init(Cipher.DECRYPT_MODE, Sender.getPublicKey());

                    hashValueOfReievedMessage = recieverMD.digest(receivedByteMessage);
                    decryptedHashValue = recieverCipherRSA.doFinal(recievedEncryptedHash);

                    System.out.println("recieved email content in hexadecimal\n");
                    hexadecimalPrint(receivedByteMessage);
                    System.out.println("\nHash of the recieved email in hexadecimal\n");
                    hexadecimalPrint(hashValueOfReievedMessage);
                    System.out.println("\nDecrypted recieved hash in hexadecimal using RSA\n");
                    hexadecimalPrint(decryptedHashValue);
                    System.out.printf("\nverfication of authencity of the recieved email %b",verify(decryptedHashValue,
                                                                                                        hashValueOfReievedMessage));
                    

                    

                }catch(Exception e){
                    System.out.println(e.getMessage());
                }

                
                



                // stringMessage = draftEmail.toString();
                // byteMessage = stringMessage.getBytes();
                // for(int bidx = 0;bidx < byteMessage.length;bidx++){
                //     stringMessageHexadecimal = stringMessageHexadecimal + String.format("%02X", byteMessage[bidx]) ;
                // }
                
                // System.out.println("the content of the email");
                // System.out.println(stringMessage);

                
                // System.out.println("the content of the email in hexadecimal\n");
                // System.out.printf("hexadecimal : %s \n",stringMessageHexadecimal);
                // try{
                //     signedSignature = emailSignature.SignEmail(Sender.getPrivateKey(), byteMessage);
                // }catch(Exception e){
                //     System.out.println(e.getMessage());
                // }
                // if(signedSignature != null){
                //     stringSignature = "";
                //     for(int bidx = 0; bidx < signedSignature.length;bidx++){
                //         stringSignature = stringSignature + String.format("%02X", signedSignature[bidx]) ;
                //     }
                //     System.out.println("\nThe signed digital signature of the email");
                //     System.out.printf("\nSignature : %s \n",stringSignature);
                // }else{
                //     System.out.println("sign failed");
                // }

                ///////////////// sending meesage
                // ReceivedByteMessage = byteMessage;
                // ReceivedByteMessage = signedSignature;
                ///////////////// sending message
                

                

                System.out.print("\n");
                System.out.println("Press any keys to return ");
                sc.nextLine();
                function_mode = FUNCTION.SELECT_FUNCTION;
                clearScreen();
            
            }

            if(function_mode == FUNCTION.EXIT){////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                System.out.println("current FUNCTION : " + String.valueOf(function_mode));
                System.out.print("\n");
                break;
            }
            

        }

        sc.close();
        System.out.print("program has successfully exited");

    }
  }