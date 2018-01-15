package mag.near.lux.util.outputformatters;

import mag.near.lux.util.tabular.TableData;

public abstract class AbstractTabularDataFormatter {
    private final TableData table;

    public AbstractTabularDataFormatter(TableData data) {
        this.table = data;
    }

    protected TableData getTable() {
        return this.table;
    }

    @Override
    public abstract String toString();

    public abstract String getFileExtension();

    public String getFileName() {
        return this.table.getIdentifier() + "." + getFileExtension();
    }
}
