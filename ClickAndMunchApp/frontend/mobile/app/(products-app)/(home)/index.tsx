import { View, Text } from 'react-native'
import React from 'react'
import { ThemedText } from '@/presentation/theme/components/themed-text'
import { useThemeColor } from '@/presentation/theme/hooks/use-theme-color'

const HomeScreen = () => {

  const primary = useThemeColor({}, 'primary');

  return (
    <View style={{ paddingTop: 100, paddingHorizontal: 20 }}>
      <ThemedText style={{ fontFamily: 'KanitBold', color: primary }}>HomeScreen</ThemedText>
      <ThemedText style={{ fontFamily: 'kanitRegular' }}>HomeScreen</ThemedText>
      <ThemedText style={{ fontFamily: 'KanitThin' }}>HomeScreen</ThemedText>

    </View>
  )
}

export default HomeScreen