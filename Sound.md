from Javadoc:

utils.Sound

# Constructor Detail #

## Sound ##
public Sound(java.lang.String url)
imports a sound

Parameters:
url - path to the soundfile (.wav,.mid ...)

# Method Detail #

## reset ##
public void reset(float pan,
> float gain)
resets the properties of the AudioPlayer playing your audiofile

Parameters:
pan - the Pan used to play the sound
gain - the gain used to play the sound
## startPlay ##
public void startPlay()
starts your sound

## startPlay ##
public void startPlay(long pan)
starts your sound with given Pan

Parameters:
pan - the Pan used to play the sound
## startPlay ##
public void startPlay(long pan,
> long gain)
starts your sound with given pan and gain

Parameters:
pan - the Pan used to play the sound
gain - the Gain used to play the sound
## startPlay ##
public void startPlay(int distance)
starts your sound with a given distance to an object

Parameters:
distance - int between 0 (loud) and ~300 (silent)
## startPlay ##
public void startPlay(java.awt.Point listener,
> java.awt.Point sound)
starts your sound with Pand and Gain dependant on 2 points

Parameters:
listener - the Point of your listener
sound - the Point at which the sound will be
## stopPlay ##
public void stopPlay()
> throws java.io.IOException
stops your sound

Throws:
java.io.IOException