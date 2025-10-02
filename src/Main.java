import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        System.out.println("Como quieres recorrer el laberinto? \n1)Manual \n2)Automatico");
        opcion = sc.nextInt();
        Laberinto laberinto = new Laberinto();
        laberinto.Main();
        if(opcion == 1){
            laberinto.jugar();
        }else if(opcion == 2) {
            laberinto.mostrarLaberinto();
            Bot botcito = new Bot(laberinto);
            botcito.recorrerLaberinto();
        }else{
            System.out.println("Opcion no permitida");
        }
       sc.close();
    }
}