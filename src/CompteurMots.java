import java.util.Scanner;

public class CompteurMots {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
       
        System.out.print("Entrez un texte : ");
        String texte = scanner.nextLine().trim();
        
        if (texte.isEmpty()) {
            System.out.println("Le texte ne contient aucun mot.");
        } else {
            String[] mots = texte.split("\\s+"); 
            int nombreDeMots = mots.length;
            
            System.out.println("Le texte contient " + nombreDeMots + " mots.");
        }
        
        scanner.close();
    }
}