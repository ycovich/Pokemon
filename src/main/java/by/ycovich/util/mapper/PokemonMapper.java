package by.ycovich.util.mapper;

import by.ycovich.dto.PokemonDTO;
import by.ycovich.model.Pokemon;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapper {
    public PokemonDTO mapToDto(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setType(pokemon.getType());
        return pokemonDTO;
    }

    private Pokemon mapToEntity(PokemonDTO pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
