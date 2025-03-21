//
//  AVAudioSession+Extensions.h
//  ChromeCast
//

#import <AVFoundation/AVFoundation.h>

@interface AVAudioSession (Extensions)

+ (void)registerForVolumeNotifications:(id)observer selector:(SEL)selector;
+ (void)unregisterForVolumeNotifications:(id)observer;

@end