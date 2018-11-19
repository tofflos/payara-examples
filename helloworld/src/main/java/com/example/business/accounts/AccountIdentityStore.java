/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.business.accounts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author Erik
 */
@RequestScoped
public class AccountIdentityStore implements IdentityStore {

    private static final Logger LOGGER = Logger.getLogger(AccountIdentityStore.class.getName());
    
    private static final List<Account> ACCOUNTS = Arrays.asList(
            new Account("administrator", "administratorPassword", "Administrator", "Employee"),
            new Account("employee", "employeePassword", "Employee"),
            new Account("guest", "guestPassword", "Guest"));

    public Optional<Account> findByNameAndCredentials(String name, String password) {
        return ACCOUNTS.stream().filter(a -> name.equals(a.getName()) && password.equals(a.getPassword())).findFirst();
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof BasicAuthenticationCredential) {
            return validate((BasicAuthenticationCredential) credential);
        }

        if (credential instanceof UsernamePasswordCredential) {
            return validate((UsernamePasswordCredential) credential);
        }

        LOGGER.info(() -> String.format("Unknown credential type: %s", credential));

        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

    public CredentialValidationResult validate(BasicAuthenticationCredential credential) {
        LOGGER.fine(() -> String.format("Validating BasicAuthenticationCredential: %s", credential));

        try {
            Optional<Account> account = findByNameAndCredentials(credential.getCaller(), credential.getPasswordAsString());

            if (account.isPresent()) {
                Account a = account.get();

                LOGGER.info(() -> String.format("Authentication succeeded for: %s with roles %s.", credential, a.getRoles()));

                return new CredentialValidationResult(a.getName(), new HashSet<>(a.getRoles()));
            }
        } catch (IllegalArgumentException ex) {
            LOGGER.info(() -> String.format("Authentication failed for: %s", credential));
        }

        return CredentialValidationResult.INVALID_RESULT;
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        LOGGER.fine(() -> String.format("Validating UsernamePasswordCredential: %s", credential));

        try {
            Optional<Account> account = findByNameAndCredentials(credential.getCaller(), credential.getPasswordAsString());

            if (account.isPresent()) {
                Account a = account.get();
                
                LOGGER.info(() -> String.format("Authentication succeeded for: %s with roles %s.", credential, a.getRoles()));
                
                return new CredentialValidationResult(a.getName(), new HashSet<>(a.getRoles()));
            }
        } catch (IllegalArgumentException ex) {
            LOGGER.info(() -> String.format("Authentication failed for: %s", credential));
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}