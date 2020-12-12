package fr.manchez.snoopy.application.AI;

import fr.manchez.snoopy.application.models.objects.Structure;

public class Direction {

    /** Direction de la structure **/
    private Moves moves;

    /** structure **/
    private Structure structure;

    public Direction(Moves moves, Structure structure) {
        this.moves = moves;
        this.structure = structure;
    }

    public Moves getMoves() {
        return moves;
    }

    public Structure getStructure() {
        return structure;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "moves=" + moves +
                ", structure=" + structure +
                '}';
    }
}
