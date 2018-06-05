package ru.iac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iac.domain.ListTable;
import ru.iac.domain.PathTable;
import ru.iac.repository.ListRepository;
import ru.iac.repository.PathRepository;
import ru.iac.utils.ReadDirAndFiles;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Service
public class PathServiceImpl implements PathService {
    private final PathRepository repository;
    private final ListRepository listRepository;
    private final ReadDirAndFiles disk;

    @Autowired
    public PathServiceImpl(PathRepository repository, ListRepository listRepository) {
        this.repository = repository;
        this.listRepository = listRepository;
        this.disk = new ReadDirAndFiles();
    }

    @Override
    public PathTable add(PathTable pathTable) {
        repository.save(pathTable);
        return pathTable;
    }

    @Override
    public List<PathTable> getAll() {
        return (List<PathTable>) repository.findAllByOrderByTimeDesc();
    }

    @Override
    public PathTable findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean addPath(String path) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (disk.isDir(path)) {
            PathTable pathTable = new PathTable();
            pathTable.setPath(path);
            pathTable.setTime(timestamp);
            add(pathTable);
            for (ListTable item: disk.getAll(pathTable)) {
                listRepository.save(item);
            }
            return true;
        } else {
            return false;
        }
    }
}
