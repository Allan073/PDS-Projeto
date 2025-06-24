package br.ufrn.imd.framework.serializers;

import br.ufrn.imd.framework.models.AbstractEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//eu amo herança
public class AbstractEntityListSerializer extends StdSerializer<List<AbstractEntity>> {

    public AbstractEntityListSerializer() {
        this(null);
    }

    public AbstractEntityListSerializer(Class<List<AbstractEntity>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<AbstractEntity> abstractEntities,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Long> ids = new ArrayList<>();
        for (AbstractEntity abstractEntity : abstractEntities) {
            ids.add(abstractEntity.getId());
        }
        generator.writeObject(ids);
    }
}
