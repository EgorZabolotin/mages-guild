package mag.near.lux.util.outputformatters;

import mag.near.lux.dto.quests.QuestsXml;

public abstract class AbstractQuestsDataFormatter {
    private final QuestsXml questsXml;

    public AbstractQuestsDataFormatter(QuestsXml data) {
        this.questsXml = data;
    }

    protected QuestsXml getQuestsXml() {
        return this.questsXml;
    }

    @Override
    public abstract String toString();

    public abstract String getFileExtension();

    public String getFileName() {
        return this.questsXml.getIdentifier() + "." + getFileExtension();
    }
}