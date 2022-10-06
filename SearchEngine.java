import java.io.IOException; //the necessary import statements for the program to function
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> searchArray = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().contains("/add")) {
            String[] params = url.getQuery().split("=");
            if (params[0].equals("s")) {
                searchArray.add(params[1]);
                return("String added!");
            }
        }
        if (url.getPath().contains("/search")) {
            String[] params = url.getQuery().split("=");
            if (params[0].equals("s")) {
                ArrayList<String> searchReturn = new ArrayList<>();
                for(String s: searchArray){
                    if(s.contains(params[1])){
                        searchReturn.add(s);
                    }
                }
                return(searchReturn.toString());
            }
        }
        return("<b>ERROR 404</b>");
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException{
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
