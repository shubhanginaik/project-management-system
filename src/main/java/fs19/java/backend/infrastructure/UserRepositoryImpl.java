package fs19.java.backend.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository


  @Override
    inMemoryUserDatabase.put(user.getId(), user);
  }

  @Override
    return new ArrayList<>(inMemoryUserDatabase.values());
  }

  @Override
    inMemoryUserDatabase.remove(user.getId());
  }

  @Override
    return Optional.ofNullable(inMemoryUserDatabase.get(id));
  }
}
