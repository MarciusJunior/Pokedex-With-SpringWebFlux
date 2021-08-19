package com.webflux.pokedex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.webflux.pokedex.model.Pokemon;
import com.webflux.pokedex.repository.PokemonRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);}
	
	
		@Bean
		CommandLineRunner init (ReactiveMongoOperations operations, PokemonRepository repository) {
			
			
			return args -> {
				Flux<Pokemon> pokemonFlux = Flux.just(
						new Pokemon(null, "Blastoise", "Shellfish","Torrent",85.5),
						new Pokemon(null, "Venusaur","Seed","Overgrow",100.0),
						new Pokemon(null, "Charizard","Flame","Blaze",90.5),
						new Pokemon(null, "Raichu", "Mouse", "Static",30.0)).flatMap(repository::save);
				
				pokemonFlux
						.thenMany(repository.findAll())
						.subscribe(System.out::println);
						
			};
			
		}
}
