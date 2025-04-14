import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Scanner;



public class main_ {

    
    public static void main(String[] args) {
        // System.out.print("\033[H\033[2J");  
        // System.out.flush();  
        user Sender = null;
        user Reciever = null;
        email ongoingEmail = new email();

        // define array of user
        // want 3 users
        // 3 keypairs
        ArrayList<user> users = new ArrayList<user>();
        ArrayList<String> publicEmailList = new ArrayList<String>();
        publicEmailList.add("alice@gmail.com");
        publicEmailList.add("bob@gmail.com");
        publicEmailList.add("jack@gmail.com");

        try{
            users.add(new user("bob",publicEmailList.get(0),KeyPairGenerator.getInstance("EC")));
            users.add(new user("alice",publicEmailList.get(1),KeyPairGenerator.getInstance("EC")));
            users.add(new user("jack",publicEmailList.get(2),KeyPairGenerator.getInstance("EC")));
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
            COMPOSE_EMAIL
        }
        int function_value = 0;
        int selected_userID = 0;
        int line_count = 1;
        String content_line = "";


        // EXIT = 0
        // SELECT_FUNCTION = 1
        // SELECT_SENDER = 2
        // SELECT_RECIEVER = 3
        // EMAIL = 4

        FUNCTION function_mode = FUNCTION.SELECT_FUNCTION;
        
        while(!exit_program){

            if(function_mode == FUNCTION.SELECT_FUNCTION){
                System.out.println("Please select function model");
                System.out.print("your input : ");

                function_value = sc.nextInt();
                while(function_value < 0 || function_value > 4){
                    System.out.println("try again!");
                    function_value = sc.nextInt();
                }

                function_mode = FUNCTION.values()[function_value];
                System.out.println("current FUNTION : " + String.valueOf(function_mode));

            }

            
            if(function_mode == FUNCTION.SELECT_SENDER){

                System.out.println("Avialable user to select as a SENDER");
                for(int uidx = 0;uidx < users.size();uidx++){
                    System.out.println("user ID : " + (uidx + 1) + " user name : " + users.get(uidx).name + " email : " + users.get(uidx).EmailAddress);
                }
                System.out.println("Please select user as a SENDER by user ID");

                selected_userID = sc.nextInt();
                while(selected_userID < 1 || selected_userID > users.size()){
                    System.out.println("invalid user ID please try again");
                    selected_userID = sc.nextInt();
                }
                selected_userID = selected_userID - 1; // transform into users list index
                Sender = users.get(selected_userID);
                System.out.println("SENDER is user ID : " + (selected_userID + 1) + " user name : " + Sender.name + " email : " + Sender.EmailAddress);
                // select user
                function_mode = FUNCTION.SELECT_FUNCTION;

            }
            else if(function_mode == FUNCTION.SELECT_RECIEVER){

                System.out.println("Avialable user to select as a RECIEVER");
                for(int uidx = 0;uidx < users.size();uidx++){
                    System.out.println("user ID : " + (uidx + 1) + " user name : " + users.get(uidx).name + " email : " + users.get(uidx).EmailAddress);
                }
                System.out.println("Please select user as a RECIEVER by user ID");

                selected_userID = sc.nextInt();
                while(selected_userID < 1 || selected_userID > users.size()){
                    System.out.println("invalid user ID please try again");
                    selected_userID = sc.nextInt();
                }
                selected_userID = selected_userID - 1; // transform into users list index
                Reciever = users.get(selected_userID);
                System.out.println("RECIEVER is user ID : " + (selected_userID + 1) + " user name : " + Reciever.name + " email : " + Reciever.EmailAddress);
                // select user
                function_mode = FUNCTION.SELECT_FUNCTION;
            }
            else if(function_mode == FUNCTION.COMPOSE_EMAIL){
                line_count = 0;
                do{
                    System.out.print("Line " + line_count + " : ");
                    content_line = sc.nextLine();
                    line_count = line_count + 1;
                    ongoingEmail.content = ongoingEmail.content + "\n" + content_line;
                    System.out.println(content_line);
                    System.out.println(content_line.getBytes());
                    System.out.println(content_line.length());
                }while(!content_line.equals("ESC"));
                System.out.println(ongoingEmail.content);

                function_mode = FUNCTION.SELECT_FUNCTION;
            }

            if(function_mode == FUNCTION.EXIT){
                break;
            }

            

        }

        sc.close();
        System.out.print("program has successfully exited");

    }
  }