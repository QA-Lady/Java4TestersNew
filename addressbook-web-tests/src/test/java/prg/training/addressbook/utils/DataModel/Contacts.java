package prg.training.addressbook.utils.DataModel;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by QA Lady  on 5/13/2017.
 */
public class Contacts extends ForwardingSet<ContactsData> {

    private Set<ContactsData> delegate;

    public Contacts(Contacts contacts) {
        this.delegate = new HashSet<ContactsData>(contacts.delegate);
    }

    public Contacts() {
        this.delegate = new HashSet<ContactsData>();
    }

    @Override
    protected Set<ContactsData> delegate() {
        return delegate;
    }

    public Contacts withAdded(ContactsData contact) {
        Contacts contacts = new Contacts(this);
        contacts.add(contact);
        return contacts;
    }

    public Contacts without(ContactsData contact) {
        Contacts contacts = new Contacts(this);
        contacts.remove(contact);
        return contacts;
    }

}
