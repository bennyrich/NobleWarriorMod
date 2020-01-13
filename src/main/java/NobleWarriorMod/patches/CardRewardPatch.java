/* AbstractDungeon.getRewardCards()

public static ArrayList<AbstractCard> getRewardCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        int numCards = 3;
        for (AbstractRelic r : player.relics) { numCards = r.changeNumberOfCardsInReward(numCards); }

        if (ModHelper.isModEnabled("Binary")) { numCards--; }
        for (int i = 0; i < numCards; i++) {
        AbstractCard.CardRarity rarity = rollRarity();
        AbstractCard card = null;

        switch (rarity) {
        case null:
        cardBlizzRandomizer = cardBlizzStartOffset;
        break;
        case EVENT:
        break;
        case CAMPFIRE:
        cardBlizzRandomizer -= cardBlizzGrowth;
        if (cardBlizzRandomizer <= cardBlizzMaxOffset) {
        cardBlizzRandomizer = cardBlizzMaxOffset;
        }
        break;
default:
        logger.info("WTF?");
        break;
        }
        boolean containsDupe = true;
        while (containsDupe) {
        containsDupe = false;
        if (player.hasRelic("PrismaticShard")) { card = CardLibrary.getAnyColorCard(rarity); } else { card = getCard(rarity); }
        for (AbstractCard c : retVal) {
        if (c.cardID.equals(card.cardID)) {
        containsDupe = true;
        }}}
        if (card != null) {
        retVal.add(card);
        }
        }
        ArrayList<AbstractCard> retVal2 = new ArrayList<>();
        for (AbstractCard c : retVal) {
        retVal2.add(c.makeCopy());
        }

        for (AbstractCard c : retVal2) {
        if (c.rarity != AbstractCard.CardRarity.RARE && cardRng.randomBoolean(cardUpgradedChance) && c.canUpgrade()) {
        c.upgrade(); continue;
        }
        if (c.type == AbstractCard.CardType.ATTACK && player.hasRelic("Molten Egg 2")) {
        c.upgrade(); continue;
        }  if (c.type == AbstractCard.CardType.SKILL && player.hasRelic("Toxic Egg 2")) {
        c.upgrade(); continue;
        }  if (c.type == AbstractCard.CardType.POWER && player.hasRelic("Frozen Egg 2")) {
        c.upgrade();
        }
        }


        return retVal2;
        }
*/