package de.lagerverwaltung.software.dto;

import de.lagerverwaltung.software.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@NoArgsConstructor
public class ItemDTOMapper implements Function<Item, ItemDTO> {

    @Override
    public ItemDTO apply(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getSpace(),
                item.getCategory().getName(),
                item.getContainer().getId()
        );
    }
}
