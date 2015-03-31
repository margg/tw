package zad3;

/**
 * @author Małgorzata Salawa
 */
public enum Table {

    EMPTY,
    ONE_PERSON,
    FULL;


    public Table leave() {
        if (this.equals(Table.FULL)) {
            return Table.ONE_PERSON;
        } else if (this.equals(Table.ONE_PERSON)) {
            return Table.EMPTY;
        } else {
            throw new IllegalStateException("leaving empty table!");
        }
    }

    public Table join() {
        if (this.equals(Table.FULL)) {
            throw new IllegalStateException("joining full table!");
        } else if (this.equals(Table.ONE_PERSON)) {
            return Table.FULL;
        } else {
            return Table.ONE_PERSON;
        }
    }


}
