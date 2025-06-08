package vesselmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vesselmod.VesselMod;
import vesselmod.misc.CustomTags;
import vesselmod.misc.SoulMechanics;
import vesselmod.powers.FreeSoulCostPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;
import static vesselmod.util.TextureLoader.getCardTextureString;


public abstract class BaseCard extends CustomCard {
    protected CardStrings cardStrings;

    protected boolean upgradesDescription;

    protected int baseCost;

    protected boolean upgradeCost;
    protected boolean upgradeDamage;
    protected boolean upgradeBlock;
    protected boolean upgradeMagic;

    protected int costUpgrade;
    protected int damageUpgrade;
    protected int blockUpgrade;
    protected int magicUpgrade;

    protected boolean baseExhaust;
    protected boolean upgExhaust;
    protected boolean baseEthereal;
    protected boolean upgEthereal;
    protected boolean baseInnate;
    protected boolean upgInnate;
    protected boolean baseRetain;
    protected boolean upgRetain;

    public boolean isSoulCostModified;
    protected boolean upgradeSoulCost;
    public boolean upgradedSoulCost;
    public int soulCost;
    public int baseSoulCost;
    protected int soulCostUpgrade;

    public BaseCard(CardInfo cardInfo) {
        this(cardInfo.baseId, cardInfo.baseCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, cardInfo.cardColor);
    }
    public BaseCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.baseId, cardInfo.baseCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, cardInfo.cardColor, upgradesDescription);
    }

    public BaseCard(String baseID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color)
    {
        super(makeID(baseID), "", getCardTextureString(baseID, cardType), cost, "", cardType, color, rarity, target);

        loadStrings();

        this.baseCost = cost;

        this.upgradesDescription = cardStrings.UPGRADE_DESCRIPTION != null;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.upgradeSoulCost = false;

        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        this.soulCostUpgrade = 0;

        initializeTitle();
        initializeDescription();
    }

    public BaseCard(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, boolean upgradesDescription)
    {
        super(makeID(cardName), "", getCardTextureString(cardName, cardType), cost, "", cardType, color, rarity, target);

        loadStrings();

        this.baseCost = cost;

        this.upgradesDescription = upgradesDescription;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.upgradeSoulCost = false;

        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        this.soulCostUpgrade = 0;

        initializeTitle();
        initializeDescription();
    }

    private void loadStrings() {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        this.rawDescription = cardStrings.DESCRIPTION;
        this.originalName = cardStrings.NAME;
        this.name = originalName;
    }

    //Methods meant for constructor use
    protected final void setDamage(int damage)
    {
        this.setDamage(damage, 0);
    }
    protected final void setBlock(int block)
    {
        this.setBlock(block, 0);
    }
    protected final void setMagic(int magic)
    {
        this.setMagic(magic, 0);
    }
    protected final void setCostUpgrade(int costUpgrade)
    {
        this.costUpgrade = costUpgrade;
        this.upgradeCost = true;
    }
    protected final void setExhaust(boolean exhaust) { this.setExhaust(exhaust, exhaust); }
    protected final void setEthereal(boolean ethereal) { this.setEthereal(ethereal, ethereal); }
    protected final void setInnate(boolean innate) {this.setInnate(innate, innate); }
    protected final void setSoulCost(int soulCost) {
        this.setSoulCost(soulCost, 0);
    }

    protected final void setDamage(int damage, int damageUpgrade)
    {
        this.baseDamage = this.damage = damage;
        if (damageUpgrade != 0)
        {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgrade;
        }
    }
    protected final void setBlock(int block, int blockUpgrade)
    {
        this.baseBlock = this.block = block;
        if (blockUpgrade != 0)
        {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgrade;
        }
    }
    protected final void setMagic(int magic, int magicUpgrade)
    {
        this.baseMagicNumber = this.magicNumber = magic;
        if (magicUpgrade != 0)
        {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgrade;
        }
    }
    protected final void setExhaust(boolean baseExhaust, boolean upgExhaust)
    {
        this.baseExhaust = baseExhaust;
        this.upgExhaust = upgExhaust;
        this.exhaust = baseExhaust;
    }
    protected final void setEthereal(boolean baseEthereal, boolean upgEthereal)
    {
        this.baseEthereal = baseEthereal;
        this.upgEthereal = upgEthereal;
        this.isEthereal = baseEthereal;
    }
    protected void setInnate(boolean baseInnate, boolean upgInnate)
    {
        this.baseInnate = baseInnate;
        this.isInnate = baseInnate;
        this.upgInnate = upgInnate;
    }

    protected final void setSoulCost(int soulCost, int soulCostUpgrade) {
        this.baseSoulCost = this.soulCost = soulCost;
        if (soulCostUpgrade != 0) {
            this.upgradeSoulCost = true;
            this.soulCostUpgrade = soulCostUpgrade;
        }
    }

    protected void setRetain(boolean baseRetain, boolean upgRetain) {
        this.baseRetain = baseRetain;
        this.selfRetain = baseRetain;
        this.upgRetain = upgRetain;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof BaseCard)
        {
            card.rawDescription = this.rawDescription;
            ((BaseCard) card).upgradesDescription = this.upgradesDescription;

            ((BaseCard) card).baseCost = this.baseCost;

            ((BaseCard) card).upgradeCost = this.upgradeCost;
            ((BaseCard) card).upgradeDamage = this.upgradeDamage;
            ((BaseCard) card).upgradeBlock = this.upgradeBlock;
            ((BaseCard) card).upgradeMagic = this.upgradeMagic;
            ((BaseCard) card).upgradeSoulCost = this.upgradeSoulCost;

            ((BaseCard) card).costUpgrade = this.costUpgrade;
            ((BaseCard) card).damageUpgrade = this.damageUpgrade;
            ((BaseCard) card).blockUpgrade = this.blockUpgrade;
            ((BaseCard) card).magicUpgrade = this.magicUpgrade;
            ((BaseCard) card).soulCostUpgrade = this.soulCostUpgrade;

            ((BaseCard) card).baseExhaust = this.baseExhaust;
            ((BaseCard) card).upgExhaust = this.upgExhaust;
            ((BaseCard) card).baseEthereal = this.baseEthereal;
            ((BaseCard) card).upgEthereal = this.upgEthereal;
            ((BaseCard) card).baseInnate = this.baseInnate;
            ((BaseCard) card).upgInnate = this.upgInnate;
        }

        return card;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            this.upgradeName();

            if (this.upgradesDescription)
            {
                if (cardStrings.UPGRADE_DESCRIPTION == null)
                {
                    VesselMod.logger.error("Card " + cardID + " upgrades description and has null upgrade description.");
                }
                else
                {
                    this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                }
            }

            if (upgradeCost)
            {
                if (isCostModified && this.cost < this.baseCost && this.cost >= 0) {
                    int diff = this.costUpgrade - this.baseCost; //how the upgrade alters cost
                    this.upgradeBaseCost(this.cost + diff);
                    if (this.cost < 0)
                        this.cost = 0;
                }
                else {
                    upgradeBaseCost(costUpgrade);
                }
            }

            if (upgradeDamage)
                this.upgradeDamage(damageUpgrade);

            if (upgradeBlock)
                this.upgradeBlock(blockUpgrade);

            if (upgradeMagic)
                this.upgradeMagicNumber(magicUpgrade);

            if (baseExhaust ^ upgExhaust)
                this.exhaust = upgExhaust;

            if (baseInnate ^ upgInnate)
                this.isInnate = upgInnate;

            if (upgradeSoulCost)
                this.upgradeSoulCost(soulCostUpgrade);

            if (baseRetain ^ upgRetain)
                this.selfRetain = upgRetain;


            this.initializeDescription();
        }
    }

    protected void upgradeSoulCost(int amount) {
        this.baseSoulCost += amount;
        this.soulCost = this.baseSoulCost;
        this.upgradedSoulCost = true;
    }

    @Override
    public void displayUpgrades() {
        if (this.upgradedCost) {
            this.isCostModified = true;
        }

        if (this.upgradedDamage) {
            this.damage = this.baseDamage;
            this.isDamageModified = true;
        }

        if (this.upgradedBlock) {
            this.block = this.baseBlock;
            this.isBlockModified = true;
        }

        if (this.upgradedMagicNumber) {
            this.magicNumber = this.baseMagicNumber;
            this.isMagicNumberModified = true;
        }

        if (this.upgradedSoulCost) {
            this.soulCost = this.baseSoulCost;
            this.isSoulCostModified = true;
        }
    }

    public boolean freeSoulCost() {
        return AbstractDungeon.player != null &&
                AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && //makes sure game doesnt crash out of combat
                this.hasTag(CustomTags.COST_SOUL) &&
                AbstractDungeon.player.hasPower(FreeSoulCostPower.POWER_ID);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (this.hasTag(CustomTags.COST_SOUL)) {
            if (this.soulCost != this.updateSoulCost()) {
                this.applyPowers();
                this.isSoulCostModified = true;
            } else this.isSoulCostModified = (this.soulCost != this.baseSoulCost);

            if (canUse && SoulMechanics.soulCount < this.updateSoulCost() && !this.hasTag(CustomTags.COST_SOUL_ALT)) {
                this.cantUseMessage = SoulMechanics.noSoulMessage;
                return false;
            } else {
                return canUse;
            }
        } else {
            return canUse;
        }
    }

    protected int updateSoulCost() {
        if (freeSoulCost()) {
            return 0;
        } else {
            return this.soulCost;
        }
    }

    /*@Override
    public void setCostForTurn(int amt) {
        if (this.costForTurn >= 0) { //normal behavior
            this.costForTurn = amt;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }

            if (this.costForTurn != this.cost) {
                this.isCostModifiedForTurn = true;
            }
        }
        int soulCostBeforeEffect = this.soulCost; //set cards from pots/discovery soul cost to 0 for one play
        if (this.costForTurn <= 0 && this.hasTag(CustomTags.COST_SOUL)) {
            this.freeSoulCost() = true;
            if (soulCostBeforeEffect != this.soulCost) {
                this.isSoulCostModified = true;
            }
        }
    }*/ //might come back to this later to make cards from discovery/pots cost 0 soul.
}