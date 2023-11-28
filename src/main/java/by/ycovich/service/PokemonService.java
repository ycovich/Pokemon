package by.ycovich.service;

import by.ycovich.dto.PokemonDTO;
import by.ycovich.dto.PokemonResponse;

public interface PokemonService {
    PokemonDTO createPokemon(PokemonDTO pokemonDto);
    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDTO getPokemonById(int id);
    PokemonDTO updatePokemon(PokemonDTO pokemonDto, int id);
    void deletePokemonId(int id);
}
