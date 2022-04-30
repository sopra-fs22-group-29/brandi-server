package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
public class BoardState {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ball> balls;

    public static List<Integer> startingPoints = List.of(0,16,32,48);

    public static List<Integer> homePoints = List.of(80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95);

    public static List<Integer> basePoints = List.of(64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79);

    // Normal cards: TWO, THREE, FIVE, SIX, EIGHT, NINE, TEN, QUEEN
    public static Map<Rank, Integer> normalCards = Map.of(Rank.TWO, 2,
            Rank.THREE, 3,
            Rank.FIVE, 5,
            Rank.SIX, 6,
            Rank.EIGHT, 8,
            Rank.NINE, 9,
            Rank.TEN, 10,
            Rank.QUEEN, 12);

    // Special Cards: FOUR, SEVEN, JACK, KING, ACE, JOKER

    public BoardState() {}

    public BoardState(ArrayList<Ball> balls) {
        this.balls = balls;
    }


    public List<Ball> getBalls() {
        return this.balls;
    }

}
