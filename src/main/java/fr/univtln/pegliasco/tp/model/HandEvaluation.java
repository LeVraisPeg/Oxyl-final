package fr.univtln.pegliasco.tp.model;

import java.util.List;

//TODO
//Ajouter des tests pour compareTo
//

public class HandEvaluation implements Comparable<HandEvaluation> {

    //Classement des mains de poker du plus faible au plus fort
    private final HandRank rank;

    // Pour départager deux mains de même rang de HandRank
    private final List<Integer> tieBreakerValues;

    public HandEvaluation(HandRank rank, List<Integer> tieBreakerValues) {
        this.rank = rank;
        this.tieBreakerValues = tieBreakerValues;
    }

    public HandRank getRank() {
        return rank;
    }

    public List<Integer> getTieBreakerValues() {
        return tieBreakerValues;
    }

    @Override
    public int compareTo(HandEvaluation other) {
        // Rank
        int rankComparison = Integer.compare(this.rank.ordinal(), other.rank.ordinal());
        if (rankComparison != 0) {
            return rankComparison;
        }

        // Valeurs secondaires
        for (int i = 0; i < 5; i++) {
            int valueComparison = Integer.compare(
                    this.tieBreakerValues.get(i),
                    other.tieBreakerValues.get(i)
            );
            if (valueComparison != 0) {
                return valueComparison;
            }
        }

        // Egalité
        return 0;
    }


    @Override
    public String toString() {
        return "HandEvaluation{" +
                "rank=" + rank +
                ", tieBreakerValues=" + tieBreakerValues +
                '}';
    }

}
