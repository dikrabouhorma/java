import java.util.Arrays;
import java.util.Scanner;

public class exo3 {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("remplire la taille du tableau:  ");
        int nombre = scanner.nextInt();

        int[] tab = new int[nombre];


        for(int i=0 ; i < nombre ; i ++){
           System.out.print("la case "+(i+1)+"=" );
           tab[i] = scanner.nextInt();
        }
   
        System.out.println("le tableau est :");

        for(int i=0 ; i < nombre ; i ++){
            System.out.println("la case "+(i+1)+"=" +tab[i] );
           
         }
        
         float avg=0;

         for(int i=0 ; i < nombre ; i ++){
            avg = tab[i]+avg;
           
         }

         System.out.println("le avg du tableau est :"+avg);

         int min=0,max=0;

         for(int i=0 ; i < nombre ; i ++){
            if(tab[i]>max){
                max = tab[i]; 
            }
            if (tab[i] < min) { 
                min = tab[i]; 
            }
            
         }

         System.out.println("le max est :"+max);
         System.out.println("le min est :"+min);

         Arrays.sort(tab);
        System.out.println("\nLe tableau trie : " + Arrays.toString(tab));

        scanner.close();
    }
}