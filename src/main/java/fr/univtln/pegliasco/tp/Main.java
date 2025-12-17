package fr.univtln.pegliasco.tp;


import fr.univtln.pegliasco.tp.model.*;

public class Main {
    public static void main(String[] args) {

        Card[] deck = new Card[52];
        int index = 0;

        for (var color : Color.values()) {
            for (Value value : Value.values()) {
                deck[index++] = new Card(color, value);
            }
        }

        // Affichage de tout le deck
        System.out.println("Deck complet :");
        for (Card card : deck) {
            System.out.println(card);
        }

        // Création d'une main de test
        Hand hand = new Hand(10);

        hand.addCard(new Card(Color.PIQUE, Value.DAME));
        hand.addCard(new Card(Color.COEUR, Value.AS));
        hand.addCard(new Card(Color.TREFLE, Value.ROI));
        hand.addCard(new Card(Color.CARREAU, Value.DIX));
        hand.addCard(new Card(Color.COEUR, Value.ROI));
        hand.addCard(new Card(Color.TREFLE, Value.DEUX));
        hand.addCard(new Card(Color.CARREAU, Value.AS));

        System.out.println("Main avant tri :");
        for (Card c : hand.getCards()) {
            System.out.println(c);
        }

        // Tri par couleur puis valeur
        hand.sortByColor();

        System.out.println("\nMain après tri (couleur puis valeur) :");
        for (Card c : hand.getCards()) {
            System.out.println(c);
        }

        // Tri par valeur puis couleur
        hand.sortByValue();

        System.out.println("\nMain après tri (valeur puis couleur) :");
        for (Card c : hand.getCards()) {
            System.out.println(c);

        }

        // Mélange de la main
        hand.shuffle();
        System.out.println("\nMain après mélange :");
        for (Card c : hand.getCards()) {
            System.out.println(c);
        }

        // Tirage de 3 cartes
        System.out.println("\nTirage de 3 cartes :");
        for (Card c : hand.Draw(3)) {
            System.out.println(c);
        }



        // --- Test de la méthode evaluate() ---
        Hand pokerHand = new Hand(5);
        //Full house
        pokerHand.addCard(new Card(Color.COEUR, Value.ROI));
        pokerHand.addCard(new Card(Color.TREFLE, Value.ROI));
        pokerHand.addCard(new Card(Color.PIQUE, Value.ROI));
        pokerHand.addCard(new Card(Color.COEUR, Value.DIX));
        pokerHand.addCard(new Card(Color.CARREAU, Value.DIX));

        System.out.println("\nMain de poker :");
        for (Card c : pokerHand.getCards()) {
            System.out.println(c);
        }

        HandEvaluation evaluation = pokerHand.evaluate();
        System.out.println("\nRésultat de evaluate() main 1: " + evaluation);


        // --- Deuxième main à comparer ---
        Hand pokerHand2 = new Hand(5);
        //Couleur
        pokerHand2.addCard(new Card(Color.COEUR, Value.DEUX));
        pokerHand2.addCard(new Card(Color.COEUR, Value.SIX));
        pokerHand2.addCard(new Card(Color.COEUR, Value.DAME));
        pokerHand2.addCard(new Card(Color.COEUR, Value.ROI));
        pokerHand2.addCard(new Card(Color.COEUR, Value.AS));


        HandEvaluation eval2 = pokerHand2.evaluate();
        System.out.println("\nRésultat de evaluate() main 2 : " + eval2);

        // --- Comparaison des deux évaluations ---
        int comparaison = evaluation.compareTo(eval2);
        System.out.println("\nComparaison main1 vs main2 (eval1.compareTo(eval2)) : " + comparaison);

        if (comparaison > 0) {
            System.out.println("Main 1 est plus forte que main 2");
        } else if (comparaison < 0) {
            System.out.println("Main 2 est plus forte que main 1");
        } else {
            System.out.println("Les deux mains sont équivalentes");
        }


    }
}
