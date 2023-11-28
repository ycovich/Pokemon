package by.ycovich.controller;

import by.ycovich.dto.PokemonDTO;
import by.ycovich.dto.PokemonResponse;
import by.ycovich.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PokemonController {
    private final PokemonService pokemonService;
    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDTO> getPokemon(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @PostMapping("/pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDTO> createPokemon(@RequestBody PokemonDTO pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("/pokemon/{id}/update")
    public ResponseEntity<PokemonDTO> updatePokemon(@RequestBody PokemonDTO pokemonDto,
                                                    @PathVariable("id") int pokemonId) {
        PokemonDTO response = pokemonService.updatePokemon(pokemonDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId) {
        pokemonService.deletePokemonId(pokemonId);
        return new ResponseEntity<>("pokemon deleted successfully", HttpStatus.OK);
    }
}
