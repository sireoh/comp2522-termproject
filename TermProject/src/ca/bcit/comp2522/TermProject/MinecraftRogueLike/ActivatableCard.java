package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card that can be activated during the game.
 * @author Vincent Fung
 * @version 2024
 */
public class ActivatableCard extends Card {
    /**
     * Constructs an {@code ActivatableCard} with the specified name.
     * @param name the name of the activatable card
     */
    public ActivatableCard(final String name) {
        super(name);
        super.setObtainedCards(handleObtainedCards(name));
        super.setCardDescription(handleDescription(name, this.getClass()));
        handleObtainedCards(name);
    }

    /**
     * Prints the details of the activatable card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(toString());
    }

    /**
     * Returns a string representation of the activatable card.
     * @return a string describing the activatable card
     */
    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder();

        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(super.getName())).append("\n");
        sb.append(formatCard("Activatable")).append("\n");
        sb.append(CARD_BORDER_LINE);

        if (super.getCardDescription() != null) {
            sb.append(formatCard(super.getCardDescription())).append("\n");
        }

        if (!super.getObtainedCards().isEmpty()) {
            sb.append(formatCard("to obtain")).append("\n");
            sb.append(formatCard("either:")).append("\n");
            sb.append(formatObtainedCards());
        }

        sb.append(CARD_BORDER_LINE);
        return sb.toString();
    }
}
