public class main {
    public static void main(String[] args){
        Scanner scanner = new scanner(system.in);
        Voiture voiture = new Voiture();

        System.out.print("entrez la couleur de la voiture:");
        String couleur = scanner.nextline();

        for(string color : voiture.couleurAutorized){
            if(color.equals(couleur)){
                voiture.couleur=color;
                break;
            }
        }

        if(found){
            System.out.println("erreur dans la saisie de la couleur!");
            System.exit(status:1);
            
        }
    }
}
