import { View, Text, KeyboardAvoidingView, ScrollView, useWindowDimensions, TextInput } from 'react-native'
import React from 'react'
import { ThemedText } from '@/presentation/theme/components/themed-text'
import ThemedTextInput from '@/presentation/theme/components/themed-text-input';
import ThemedButton from '@/presentation/theme/components/themed-button';
import ThemedLink from '@/presentation/theme/components/themed-link';

const LoginScreen = () => {

  const { height } = useWindowDimensions();

    return (

      <KeyboardAvoidingView behavior='padding' style={{ flex: 1 }}>
        <ScrollView
          style={{
            paddingHorizontal: 40,
          }}
        >
          <View
            style={{
              paddingTop: height * 0.35,
            }}
          >
            <ThemedText type='title'>Ingresar</ThemedText>
            <ThemedText style={{ color: 'grey', paddingTop: 10, paddingBottom: 20 }}>Por favor ingrese para continuar</ThemedText>
          </View>

          
          <View>
            <ThemedTextInput
              placeholder='Correo electronico'
              keyboardType='email-address'
              autoCapitalize='none'
              icon='mail-outline'
            />
            
            <ThemedTextInput
              placeholder='Contraseña'
              secureTextEntry
              autoCapitalize='none'
              icon='lock-closed-outline'
            />
          </View>

          <View style={{ marginTop: 10 }} />

          <ThemedButton>Ingresar</ThemedButton>

          <View style={{ marginTop: 50 }} />

          <View style={{
            flexDirection: 'row',
            justifyContent: 'center',
            alignItems: 'center'
          }}>
              <ThemedText style={{marginHorizontal: 10}}>No tienes cuenta?</ThemedText>
              <ThemedLink href='/auth/register' >
                Crear cuenta
              </ThemedLink>

          </View>

        </ScrollView>

      </KeyboardAvoidingView>
    )
}

export default LoginScreen