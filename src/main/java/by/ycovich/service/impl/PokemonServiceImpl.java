package by.ycovich.service.impl;

import by.ycovich.dto.PokemonDTO;
import by.ycovich.dto.PokemonResponse;
import by.ycovich.exception.PokemonNotFoundException;
import by.ycovich.model.Pokemon;
import by.ycovich.repository.PokemonRepository;
import by.ycovich.service.PokemonService;
import by.ycovich.util.mapper.PokemonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;
    private final PokemonMapper pokemonMapper;
    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository, PokemonMapper pokemonMapper) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonMapper = pokemonMapper;
    }

    @Override
    public PokemonDTO createPokemon(PokemonDTO newbornPokemonDTO) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(newbornPokemonDTO.getName());
        pokemon.setType(newbornPokemonDTO.getType());

        Pokemon newPokemon = pokemonRepository.save(pokemon);

        PokemonDTO pokemonResponse = new PokemonDTO();
        pokemonResponse.setId(newPokemon.getId());
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());
        return pokemonResponse;
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pageOfPokemon = pokemonRepository.findAll(pageable);

        List<Pokemon> listOfPokemon = pageOfPokemon.getContent();
        List<PokemonDTO> content = listOfPokemon.stream().map(pokemonMapper::mapToDto).collect(Collectors.toList()); //

        return PokemonResponse.builder()
                .content(content)
                .page(pageOfPokemon.getNumber())
                .pageSize(pageOfPokemon.getSize())
                .totalElements(pageOfPokemon.getTotalElements())
                .totalPages(pageOfPokemon.getTotalPages())
                .last(pageOfPokemon.isLast())
                .build();
    }

    @Override
    public PokemonDTO getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository
                .findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("pokemon not found"));
        return pokemonMapper.mapToDto(pokemon);
    }

    @Override
    public PokemonDTO updatePokemon(PokemonDTO updatedPokemonDto, int id) {
        Pokemon pokemonToBeUpdated = pokemonRepository
                .findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("update failure"));

        pokemonToBeUpdated.setName(updatedPokemonDto.getName());
        pokemonToBeUpdated.setType(updatedPokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemonToBeUpdated);
        return pokemonMapper.mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemonId(int id) {
        Pokemon pokemonToBeDeleted = pokemonRepository
                .findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("delete failure"));
        pokemonRepository.delete(pokemonToBeDeleted);
    }

}
