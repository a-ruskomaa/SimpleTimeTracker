package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fxTyoaika.SampleData;
import fxTyoaika.model.DataObject;
import fxTyoaika.model.Entry;
import fxTyoaika.model.IdGenerator;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;

public class EntryDAO extends AbstractDAO<Entry> {


    @Override
    public Entry create(Entry object) {
        ArrayList<Entry> entries = (ArrayList<Entry>) SampleData.getData(Entry.class);
        
        Entry entry = new Entry(IdGenerator.getNewEntryId(), object.getOwner(), object.getStartDateTime(), object.getEndDateTime());
        
        entries.add(entry);
        
        return entry;
    }

    @Override
    public List<Entry> getData() {
        // TODO Auto-generated method stub
        return (ArrayList<Entry>) SampleData.getData(Entry.class);
    }
    
    @Override
    public List<Entry> list() {
        return list(Entry.class);
    }
    
    public List<Entry> list(Project project) {
        return list(Entry.class, project);
    }

}
