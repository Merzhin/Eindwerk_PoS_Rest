/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Logo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Pieter
 */
public interface LogoJpaRepository extends JpaRepository<Logo, Long> {

}