import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;




public class main_ {

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    // public static void hexadecimalPrint(byte[] bytes,boolean useLimit,int limit){
    //     int counter = 0;
    //     for(int bidx = 0;bidx < bytes.length;bidx++){
    //         if(useLimit && counter >= limit){
    //             break;
    //         }
    //         counter++;
    //         System.out.print(String.format("%02X", bytes[bidx]));
    //     }
    //     if(useLimit){
    //         System.out.print(".....");
    //     }
    // }

    public static void show2emailSideBySide(String[] emailLine1,String[] emailLine2){
        int maxLength = (emailLine1.length > emailLine2.length) ? emailLine1.length : emailLine2.length;
        String contentLine1;
        String contentLine2;
        for(int lidx = 0; lidx < maxLength;lidx++){
            if(lidx < emailLine1.length){
                contentLine1 = emailLine1[lidx];
            }else{
                contentLine1 = "";
            }

            if(lidx < emailLine2.length){
                contentLine2 = emailLine2[lidx];
            }else{
                contentLine2 = "";
            }
            System.out.printf("Line %-2d : %-50s | Line %-2d : %-50s\n",lidx,contentLine1,lidx,contentLine2);
        }
    }

    public static void show1email(String[] emailLine){
        for(int lidx = 0;lidx < emailLine.length;lidx++){
            System.out.printf("Line %-2d : %-50s \n",lidx,emailLine[lidx]);
        }
    }

    public static String getHexadecimalString(byte[] bytes,boolean useLimit,int limit){
        String hex = "";
        int counter = 0;
        for(int bidx = 0;bidx < bytes.length;bidx++){
            if(useLimit && counter >= limit){
                break;
            }
            hex = hex + String.format("%02X", bytes[bidx]);
            counter++;
        }
        return hex;
    } 

    public static boolean isTheSameHash(byte[] hash1,byte[] hash2){
        if(hash1.length != hash2.length){
            return false;
        }
        for(int bidx = 0;bidx < hash1.length;bidx++){
            if(hash1[bidx] != hash2[bidx]){
                return false;
            }
        }
        return true;
    }

    public static String[] changeContent(String[] originalMessage){
        String senderAddressLine = originalMessage[0];
        String recieverAddressLine = originalMessage[1];
        String[] modifiedMessage = new String[5];
        modifiedMessage[0] = senderAddressLine;
        modifiedMessage[1] = recieverAddressLine;
        modifiedMessage[2] = "Congratulation";
        modifiedMessage[3] = "You just won $99999999 ";
        modifiedMessage[4] = "follow this link -> http:xxx to claim prize";
        return modifiedMessage;
        
    }

    public static byte[] attackerResendByte(String[] modifiedMessageLines){
        String message = "";
        for(int sidx = 0; sidx < modifiedMessageLines.length;sidx++){
            message = message + modifiedMessageLines[sidx] + "\n";
        }
        return message.getBytes();
    }

    public static String byteToString(byte[] message){
        String stringMessage = "";
        for(int bidx = 0;bidx < message.length;bidx++){
            stringMessage = stringMessage + (char) message[bidx];
        }
        return stringMessage;
    }

    public static byte[] replaceSignature(byte[] messageByte,PrivateKey PK) throws NoSuchAlgorithmException, NoSuchPaddingException,InvalidKeyException,IllegalBlockSizeException,BadPaddingException{
        try{
            MessageDigest MD = MessageDigest.getInstance("SHA256");
            Cipher CipherRSA = Cipher.getInstance("RSA");
            CipherRSA.init(Cipher.ENCRYPT_MODE, PK);
            return CipherRSA.doFinal(MD.digest(messageByte));


        }catch(Exception e){
            System.out.println("throwing exception from method : replaceSignature");
            throw e;
        }
        
    }


    public static void main(String[] args) {
        // System.out.print("\033[H\033[2J");  
        // System.out.flush();  
        user Sender = null;
        user Reciever = null;
        email draftEmail = new email();
        ArrayList<String> draftedContent;

        ///////////////////////////////////////////////
        // for simulating sending
        MessageDigest senderMD = null;
        Cipher senderCipherRSA;
        byte[] sentEncryptedHash;
        String stringMessage;
        byte[] sentByteMessage;
        byte[] hashValue;


        //// ongoing message
        
        byte[] packetByteMessage;
        byte[] packetByteEncryptedHash;

        /// 


        
        ///////////////// Reciever
        
        PublicKey selectedKeyForVerification;
        String keyOwnerName;
        boolean isMessageModified = true;
        boolean unableToDecrypt = false;
        Cipher recieverCipherRSA;
        MessageDigest recieverMD;
        String errorCause = "";
        byte[] recievedEncryptedHash;
        byte[] receivedByteMessage;
        byte[] decryptedHashValue;
        byte[] hashValueOfReievedMessage;


        /////////////////////////// attacker
        
        String[] messageLines;
        int selectModificationMethodID;
        
        enum MODIFY_METHOD{
            CHOOSE_METHOD,
            CHANGE_SENDER,
            CHANGE_CONTENT,
            RESIGN_MESSAGE,
            TOTAL_MODIFICATION,
            PASS_MESSAGE
        }

        MODIFY_METHOD selected_method;
        
        
        /////////////////////////// attacker

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
        user attacker = null;

        try{
            users.add(new user("alice",
                                    publicEmailList.get(0),
                                    KeyPairGenerator.getInstance("RSA")));
            users.add(new user("bob",
                                    publicEmailList.get(1),
                                    KeyPairGenerator.getInstance("RSA")));
            users.add(new user("jack",
                                    publicEmailList.get(2),
                                    KeyPairGenerator.getInstance("RSA")));
            attacker = new user("Attacker", 
                            "attacker@gmail.com",
                                    KeyPairGenerator.getInstance("RSA"));
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
                

            }// end SELECT_FUNCTION






























            
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

            } // end SELECT_SENDER



























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
            } //end SELECT_RECIEVER























            else if(function_mode == FUNCTION.COMPOSE_EMAIL){////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("current FUNTION : " + String.valueOf(function_mode));
                
                // System.out.println("Start compose email by typing each line");
                // System.out.println("type ESC to escape");
                // System.out.println("press ENTER to new line");
                line_count = 0;
                draftedContent = new ArrayList<>();
                do{
                    System.out.println("type 'ESC' case sensitive to escape\n");
                    System.out.println("press ENTER to new line\n");
                    System.out.println("a line can only contain 50 character before automatic new line is used\n");
                    for(int lidx = 0; lidx < line_count;lidx++){
                        System.out.printf("Line %d : %s\n",lidx,draftedContent.get(lidx));
                    }
                    System.out.print("Line " + line_count + " : ");
                    content_line = sc.nextLine();
                    // line_count++;
                    if(content_line.equals("ESC")){
                        break;
                    }else if(content_line.equals("RESET")){
                        line_count = 0;
                        draftedContent = new ArrayList<>();
                        clearScreen();
                        continue;
                    }
                    // # make sure the line contains less than 50 character
                    while(content_line.length() > 50){
                        draftedContent.add(content_line.substring(0, 50));
                        content_line = content_line.substring(50,content_line.length());
                        line_count++;
                    }
                    if(content_line.length() != 0){
                        draftedContent.add(content_line);
                        line_count++;
                    }
                    
                    clearScreen();
                }while(!content_line.equals("ESC"));
                draftEmail.content = draftedContent;

                function_mode = FUNCTION.VIEW_EMAIL;
                clearScreen();
            } // end COMPOSE EMAIL






























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

                show1email(draftEmail.getContentArray());
                // for(int lidx = 0;lidx < draftEmail.content.size();lidx++){
                //     System.out.printf("line %d : %s \n",lidx,draftEmail.content.get(lidx));
                // }

                System.out.print("\n");
                System.out.println("Press any keys to return ");
                sc.nextLine();
                function_mode = FUNCTION.SELECT_FUNCTION;
                clearScreen();

            } /// end VIEW_EMAIL























            else if(function_mode == FUNCTION.SIMULATE_SENDING){ ////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                clearScreen();
                try{
                    isMessageModified = true;
                    errorCause = "";
                    unableToDecrypt = false;
                    selectedKeyForVerification = null;
                    senderMD = MessageDigest.getInstance("SHA256");
                    senderCipherRSA = Cipher.getInstance("RSA");

                    stringMessage = draftEmail.toString();
                    sentByteMessage = stringMessage.getBytes();
                    hashValue = senderMD.digest(sentByteMessage);
                    
                    senderCipherRSA.init(Cipher.ENCRYPT_MODE, Sender.getPrivateKey());
                    sentEncryptedHash = senderCipherRSA.doFinal(hashValue);

                    packetByteEncryptedHash = sentEncryptedHash.clone();
                    packetByteMessage = sentByteMessage.clone();

                   



                    selected_method = MODIFY_METHOD.CHOOSE_METHOD;

                    while(selected_method != MODIFY_METHOD.PASS_MESSAGE){

                        System.out.printf("       Email Message to modify\n\n");
                        show1email(byteToString(packetByteMessage).split("\n"));

                        System.out.printf("\n       Intercepted Email Byte Stream\n");
                        System.out.printf("\n   %s\n",getHexadecimalString(packetByteMessage, true,20) + " ....");

                        System.out.printf("\n       Intercepted Encrypted Hash Byte Stream\n");
                        System.out.printf("\n   %s\n\n",getHexadecimalString(packetByteEncryptedHash, true,20) + " ....");
                        
                        System.out.println("As an ATTACKER, how would you like to modifiy the message??\n");

                        System.out.println("please select method by command id");
                        for(int midx = 0;midx < MODIFY_METHOD.values().length;midx++){
                            System.out.printf("id : %d command : %s\n",midx,MODIFY_METHOD.values()[midx].toString());
                        }

                        selectModificationMethodID = -1;
                        while(selectModificationMethodID == -1){
                            try{
                                System.out.print("selecting method with integer : ");
                                selectModificationMethodID = Integer.parseInt(sc.nextLine());
                            }catch(Exception e){
                                System.out.println("given integer is not an integer please try again");
                                continue;
                            }
                            if(selectModificationMethodID < 0 || selectModificationMethodID > MODIFY_METHOD.values().length){
                                System.out.println("given integer is out of bound, please try again!");
                                selectModificationMethodID = -1;
                            }
                        }

                        selected_method = MODIFY_METHOD.values()[selectModificationMethodID];

                        switch (selected_method) {
                            case MODIFY_METHOD.CHANGE_SENDER:

                                messageLines = byteToString(packetByteMessage).split("\n");
                                messageLines[0] = messageLines[0].split(":")[0] + ": " + attacker.EmailAddress;
                                packetByteMessage = attackerResendByte(messageLines);
                                break;

                            case MODIFY_METHOD.CHANGE_CONTENT:
                                messageLines = byteToString(packetByteMessage).split("\n");
                                messageLines = changeContent(messageLines);
                                packetByteMessage = attackerResendByte(messageLines);
                                break;
                            case MODIFY_METHOD.RESIGN_MESSAGE:
                                packetByteEncryptedHash = replaceSignature(packetByteMessage, attacker.getPrivateKey());
                                break;
                            case MODIFY_METHOD.TOTAL_MODIFICATION:
                                clearScreen();
                                break;
                            case MODIFY_METHOD.PASS_MESSAGE:
                                clearScreen();
                                break;
                            default:
                                clearScreen();
                                break;
                        }
                        
                        clearScreen();
                        
                    }
                    
                    clearScreen();
                    

                    //////////////////// ATTACKING
                    
                    


                    /////////// recieving end
                    

                    
                    /////////// recieving end
                    recievedEncryptedHash = packetByteEncryptedHash.clone();
                    receivedByteMessage = packetByteMessage.clone();

                    recieverMD = MessageDigest.getInstance("SHA256");
                    
                    hashValueOfReievedMessage = recieverMD.digest(receivedByteMessage);

                    System.out.printf("       Recieved Email\n\n");
                    show1email(byteToString(receivedByteMessage).split("\n"));

                    System.out.printf("\n       Recieved Email Byte Stream\n");
                    System.out.printf("\n   %s\n",getHexadecimalString(receivedByteMessage, true,20) + " ....");

                    System.out.printf("\n       Recieved Encrypted Hash Byte Stream\n");
                    System.out.printf("\n   %s\n",getHexadecimalString(recievedEncryptedHash, true,20) + " ....");
                    

                    //////////////////// VERIFICATION
                    

                    System.out.println("\nAs the RECIEVER, choose a public key to verify the authencity of the recieved Email message\n");
                    System.out.println("Please select a public key by selecting user ID\n");
                    for(int uidx = 0; uidx < users.size();uidx++){
                        System.out.printf("type %s to use the public key of user : %s email-address : %s  \n",
                                            uidx + 1,
                                            users.get(uidx).name,
                                            users.get(uidx).EmailAddress);
                    }
                    System.out.println("\n");
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

                    selected_userID = selected_userID - 1;
                    selectedKeyForVerification = users.get(selected_userID).getPublicKey();
                    keyOwnerName = users.get(selected_userID).name;

                    clearScreen();

                    recieverCipherRSA = Cipher.getInstance("RSA");
                    try{
                        recieverCipherRSA.init(Cipher.DECRYPT_MODE, selectedKeyForVerification);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("Exception from inner Try block=01");
                        System.out.println("The message must have been altered!!");
                    }
                    
                    try {
                        decryptedHashValue = recieverCipherRSA.doFinal(recievedEncryptedHash);
                    } catch (Exception e) {
                        // System.out.println(e.getMessage());
                        // System.out.println("Exception from inner Try block=02");
                        // System.out.println("The message must have been altered!!");
                        errorCause = e.getMessage();
                        unableToDecrypt = true;
                        decryptedHashValue = null;
                    }
                    
                    
                    
                    
                    // System.out.printf("\nHash of the recieved email in hexadecimal : %s .....\n",getHexadecimalString(hashValueOfReievedMessage,true,10));
                    
                    isMessageModified = unableToDecrypt ? true : !isTheSameHash(decryptedHashValue,hashValueOfReievedMessage);

                    System.out.print("\n\n");
                    // System.out.printf("Line %-2d : %-50s | Line %-2d : %-50s\n");
                    System.out.printf("-== %-20s ==------------------------------------------------------------------------------------------------\n","EMAIL COMPARISON");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       Original Email %-39s|        Recieved Email\n","","");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    show2emailSideBySide(byteToString(sentByteMessage).split("\n"), byteToString(receivedByteMessage).split("\n"));
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       %-50s    |        %-50s\n","Sent Email Byte Stream","Recieved Email Byte Stream");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("   %-50s        |    %-50s\n",getHexadecimalString(sentByteMessage, true,20) + " ....",getHexadecimalString(receivedByteMessage, true,20) + " ....");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       %-50s    |        %-50s\n","Sent Hash","Hash of the recieved Email Byte Stream");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("   %-50s        |    %-50s\n",getHexadecimalString(hashValue, true,20) + " ....",getHexadecimalString(hashValueOfReievedMessage, true,20) + " ....");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       %-50s    |        %-50s\n","Sent Encrypted Hash","Recieved Encrypted Hash");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("   %-50s        |    %-50s\n",getHexadecimalString(sentEncryptedHash, true,20) + " ....",getHexadecimalString(recievedEncryptedHash, true,20) + " ....");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("-== %-20s ==------------------------------------------------------------------------------------------------\n","VERIFICATION PROCESS");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       %-50s    |\n","Public key used , Owner : " + keyOwnerName);
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("   %-50s        |\n",getHexadecimalString(selectedKeyForVerification.getEncoded(), true,20) + " ....");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       %-50s    |        %-50s\n","Hash of the recieved Email Byte Stream","Decrypted of recieved encrypted Hash");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("   %-50s        |    %-50s\n",getHexadecimalString(hashValueOfReievedMessage, true,20) + " ....",unableToDecrypt ? "UNABLE TO DECRYPT cuase : " + errorCause :  getHexadecimalString(decryptedHashValue , true,20) + " ....");
                    System.out.printf("       %-50s    |        %-50s\n","","");
                    System.out.printf("       VERIFICATION STATUS : %s\n",isMessageModified ? "MODIFIED" : "UN-MODIFIED");


                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Exception from outer {verification} Try block");
                } 


                System.out.print("\n");
                System.out.print("Press any keys to return ");
                sc.nextLine();
                function_mode = FUNCTION.SELECT_FUNCTION;
                clearScreen();
            
            }// end SIMULATE SENDING














            if(function_mode == FUNCTION.EXIT){////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                System.out.println("current FUNCTION : " + String.valueOf(function_mode));
                System.out.print("\n");
                break;
            } // end EXIT





            
            

        }

        sc.close();
        System.out.print("program has successfully exited");

    }
  }