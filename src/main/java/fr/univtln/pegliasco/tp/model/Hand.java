package fr.univtln.pegliasco.tp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private Card[] cards;
    private int cardCount;

    public Hand(int maxCards) {
        this.cards = new Card[maxCards];
        this.cardCount = 0;
    }

    public void addCard(Card card) {
        if (cardCount < cards.length) {
            cards[cardCount++] = card;
        } else {
            throw new IllegalStateException("Main pleine, impossible d'ajouter une carte.");
        }
    }

    public Card[] getCards() {
        Card[] handCards = new Card[cardCount];
        System.arraycopy(cards, 0, handCards, 0, cardCount);
        return handCards;
    }

    public void sortByColor() {
        List<Card> list = new ArrayList<>(
                Arrays.asList(cards).subList(0, cardCount)
        );
        list.sort(
                Comparator
                        .comparing(Card::getColor)
                        .thenComparing(c -> c.getValue().getValue())
        );
        for (int i = 0; i < cardCount; i++) {
            cards[i] = list.get(i);
        }
    }

    public void sortByValue() {
        List<Card> list = new ArrayList<>(
                Arrays.asList(cards).subList(0, cardCount)
        );
        list.sort(
                Comparator
                        .comparing((Card c) -> c.getValue().getValue())
                        .thenComparing(Card::getColor)
        );
        for (int i = 0; i < cardCount; i++) {
            cards[i] = list.get(i);
        }
    }

    public void shuffle() {
        List<Card> list = new ArrayList<>(
                Arrays.asList(cards).subList(0, cardCount)
        );
        java.util.Collections.shuffle(list);
        for (int i = 0; i < cardCount; i++) {
            cards[i] = list.get(i);
        }
    }

    public List<Card> Draw(int n) {
        if (n > cardCount) {
            throw new IllegalArgumentException("Pas assez de carte " + n + " cartes.");
        }
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            drawnCards.add(cards[--cardCount]);
            // Libère la référence
            cards[cardCount] = null;
        }
        return drawnCards;
    }


    public HandEvaluation evaluate() {
        if (cardCount != 5) {
            throw new IllegalStateException("Une main de poker doit contenir exactement 5 cartes.");
        }


        sortByValue();
        List<Card> handList = Arrays.asList(getCards());

        // Comptage des occurrences de chaque valeur
        java.util.Map<Integer, Integer> valueCounts = new java.util.HashMap<>();
        for (Card c : handList) {
            int v = c.getValue().getValue();
            valueCounts.put(v, valueCounts.getOrDefault(v, 0) + 1);
        }

        boolean flush = isFlush(handList);
        boolean straight = isStraight(handList);

        // Trie par fréquence desc puis valeur desc
        List<java.util.Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(valueCounts.entrySet());
        entries.sort((e1, e2) -> {
            int byCount = Integer.compare(e2.getValue(), e1.getValue());
            return (byCount != 0) ? byCount : Integer.compare(e2.getKey(), e1.getKey());
        });

        // Construction des 5 valeurs de départage
        List<Integer> tieBreakerValues = new ArrayList<>(5);
        for (java.util.Map.Entry<Integer, Integer> e : entries) {
            for (int i = 0; i < e.getValue(); i++) {
                tieBreakerValues.add(e.getKey());
            }
        }

        HandRank rank;
        if (straight && flush) {
            rank = HandRank.QUINTE_FLUSH;
        } else if (valueCounts.containsValue(4)) {
            rank = HandRank.CARRE;
        } else if (valueCounts.containsValue(3) && valueCounts.containsValue(2)) {
            rank = HandRank.FULL_HOUSE;
        } else if (flush) {
            rank = HandRank.COULEUR;
        } else if (straight) {
            rank = HandRank.QUINTE;
        } else if (valueCounts.containsValue(3)) {
            rank = HandRank.BRELAN;
        } else if (java.util.Collections.frequency(valueCounts.values(), 2) == 2) {
            rank = HandRank.DOUBLE_PAIRE;
        } else if (valueCounts.containsValue(2)) {
            rank = HandRank.PAIRE;
        } else {
            rank = HandRank.CARTE;
        }

        return new HandEvaluation(rank, tieBreakerValues);
    }


    //On vérifie si toutes les cartes ont la même couleur
    private boolean isFlush(List<Card> hand) {
        Color first = hand.get(0).getColor();
        for (Card c : hand) {
            if (c.getColor() != first) {
                return false;
            }
        }
        return true;
    }

    //On vérifie si les valeurs des cartes sont consécutives
    //On a trier la main avant
    private boolean isStraight(List<Card> hand) {
        int prev = hand.get(0).getValue().getValue();
        for (int i = 1; i < hand.size(); i++) {
            int current = hand.get(i).getValue().getValue();
            if (current != prev + 1) {
                return false;
            }
            prev = current;
        }
        return true;
    }

}
