// @author: Philipp Jean-Jacques

package core.graphics.filter;

import java.awt.image.BufferedImage;

public class SuperSaIFilter extends GraphicsFilter {

    /* Copyright (C) 1997-2001 ZSNES Team ( zsknight@zsnes.com / _demo_@zsnes.com )

    * This program is free software; you can redistribute it and/or
    * modify it under the terms of the GNU General Public License
    * as published by the Free Software Foundation; either
    * version 2 of the License, or (at your option) any later
    * version.

    * This program is distributed in the hope that it will be useful,
    * but WITHOUT ANY WARRANTY; without even the implied warranty of
    * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    * GNU General Public License for more details.

    * You should have received a copy of the GNU General Public License
    * along with this program; if not, write to the Free Software
    * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.

    * This Program was ported to Java from the Original C++ Code at http://zsnes.cvs.sourceforge.net/viewvc/zsnes/zsnes/src/video/2xsai.cpp?content-type=text%2Fplain

    */

    static int colorMask = 0xF7DEF7DE;
    static int lowPixelMask = 0x08210821;
    static int qcolorMask = 0xE79CE79C;
    static int qlowpixelMask = 0x18631863;
    static int redblueMask = 0xF81F;
    static int greenMask = 0x7E0;

    public SuperSaIFilter(){
    }

    @Override
    public BufferedImage apply(BufferedImage img){

        Init_2xSaI(img.BITMASK);

        return img; // TODO: MAKE THIS THING WORKING!!

    }

    public void Init_2xSaI(int BitFormat)
    {

        // SET THE COLOR MASKS

        if (BitFormat == 565)
        {
            colorMask = 0xF7DEF7DE;
            lowPixelMask = 0x08210821;
            qcolorMask = 0xE79CE79C;
            qlowpixelMask = 0x18631863;
            redblueMask = 0xF81F;
            greenMask = 0x7E0;
        }
        else
        if (BitFormat == 555)
        {
            colorMask = 0x7BDE7BDE;
            lowPixelMask = 0x04210421;
            qcolorMask = 0x739C739C;
            qlowpixelMask = 0x0C630C63;
            redblueMask = 0x7C1F;
            greenMask = 0x3E0;
        }
        else
        {
            return;
        }

        // Init_2xSaIMMX(BitFormat);

        return;

    }


    private static int GetResult1(int A, int B, int C, int D, int E){
        int x = 0;
        int y = 0;
        int r = 0;
        if (A == C) x+=1; else if (B == C) y+=1;
        if (A == D) x+=1; else if (B == D) y+=1;
        if (x <= 1) r+=1;
        if (y <= 1) r-=1;
        return r;
    }

    private static int GetResult2(int A, int B, int C, int D, int E){
        int x = 0;
        int y = 0;
        int r = 0;
        if (A == C) x+=1; else if (B == C) y+=1;
        if (A == D) x+=1; else if (B == D) y+=1;
        if (x <= 1) r-=1;
        if (y <= 1) r+=1;
        return r;
    }


    private static int GetResult(int A, int B, int C, int D){
        int x = 0;
        int y = 0;
        int r = 0;
        if (A == C) x+=1; else if (B == C) y+=1;
        if (A == D) x+=1; else if (B == D) y+=1;
        if (x <= 1) r+=1;
        if (y <= 1) r-=1;
        return r;
    }


    static int INTERPOLATE(int A, int B){
        if (A !=B)
        {
           return ( ((A & colorMask) >> 1) + ((B & colorMask) >> 1) + (A & B & lowPixelMask) );
        }
        else return A;
    }


    public static int Q_INTERPOLATE(int A, int B, int C, int D)
    {
        int x = ((A & qcolorMask) >> 2) +
                            ((B & qcolorMask) >> 2) +
                            ((C & qcolorMask) >> 2) +
                            ((D & qcolorMask) >> 2);
        int y = (A & qlowpixelMask) +
                            (B & qlowpixelMask) +
                            (C & qlowpixelMask) +
                            (D & qlowpixelMask);
        y = (y >> 2) & qlowpixelMask;
        return x+y;
    }


//#define BLUE_MASK565 0x001F001F
//#define RED_MASK565 0xF800F800
//#define GREEN_MASK565 0x07E007E0

//#define BLUE_MASK555 0x001F001F
//#define RED_MASK555 0x7C007C00
//#define GREEN_MASK555 0x03E003E0


//srcPtr        equ 8
//deltaPtr      equ 12
//srcPitch      equ 16
//width         equ 20
//dstOffset     equ 24
//dstPitch      equ 28
//dstSegment    equ 32

public void Super2xSaI(int srcPtr, int deltaPtr, int srcPitch, int width, int dstPtr , int dstPitch){

    int dP;
    int bP;
    int inc_bP;
    int height = 1;
    int dPitch = dstPitch >> 1;
    int Nextline = srcPitch >> 1;

    {
        inc_bP = 1;

        //  for (height; height; height-=1)
	{
	    bP = srcPtr;
            dP = dstPtr;
            for (int finish = width; finish > 0; finish -= inc_bP )
            {
                int color4, color5, color6;
                int color1, color2, color3;
                int colorA0, colorA1, colorA2, colorA3,
                    colorB0, colorB1, colorB2, colorB3,
                    colorS1, colorS2;
                int product1a, product1b, product2a, product2b;

            //---------------------------------------    B1 B2
            //                                         4  5  6 S2
            //                                         1  2  3 S1
            //                                           A1 A2

            colorB0 = (bP- Nextline - 1);
            colorB1 = (bP- Nextline);
            colorB2 = (bP- Nextline + 1);
            colorB3 = (bP- Nextline + 2);

            color4 = (bP - 1);
            color5 = (bP);
            color6 = (bP + 1);
            colorS2 = (bP + 2);

            color1 = (bP + Nextline - 1);
            color2 = (bP + Nextline);
            color3 = (bP + Nextline + 1);
            colorS1 = (bP + Nextline + 2);

            colorA0 = (bP + Nextline + Nextline - 1);
            colorA1 = (bP + Nextline + Nextline);
            colorA2 = (bP + Nextline + Nextline + 1);
            colorA3 = (bP + Nextline + Nextline + 2);

            //--------------------------------------

                if (color2 == color6 && color5 != color3)
                {
                   product2b = product1b = color2;
                }
                else
                if (color5 == color3 && color2 != color6)
                {
                   product2b = product1b = color5;
                }
                else
                if (color5 == color3 && color2 == color6)
                {
                   int r = 0;

                   r += GetResult (color6, color5, color1, colorA1);
                   r += GetResult (color6, color5, color4, colorB1);
                   r += GetResult (color6, color5, colorA2, colorS1);
                   r += GetResult (color6, color5, colorB2, colorS2);

                   if (r > 0)
                      product2b = product1b = color6;
                   else
                   if (r < 0)
                      product2b = product1b = color5;
                   else
                   {
                      product2b = product1b = INTERPOLATE (color5, color6);
                   }

                }
                else
                {

                   if (color6 == color3 && color3 == colorA1 && color2 != colorA2 && color3 != colorA0)
                      product2b = Q_INTERPOLATE (color3, color3, color3, color2);
                   else
                   if (color5 == color2 && color2 == colorA2 && colorA1 != color3 && color2 != colorA3)
                      product2b = Q_INTERPOLATE (color2, color2, color2, color3);
                   else
                      product2b = INTERPOLATE (color2, color3);

                   if (color6 == color3 && color6 == colorB1 && color5 != colorB2 && color6 != colorB0)
                      product1b = Q_INTERPOLATE (color6, color6, color6, color5);
                   else
                   if (color5 == color2 && color5 == colorB2 && colorB1 != color6 && color5 != colorB3)
                      product1b = Q_INTERPOLATE (color6, color5, color5, color5);
                   else
                      product1b = INTERPOLATE (color5, color6);
                }

                if (color5 == color3 && color2 != color6 && color4 == color5 && color5 != colorA2)
                   product2a = INTERPOLATE (color2, color5);
                else
                if (color5 == color1 && color6 == color5 && color4 != color2 && color5 != colorA0)
                   product2a = INTERPOLATE(color2, color5);
                else
                   product2a = color2;

                if (color2 == color6 && color5 != color3 && color1 == color2 && color2 != colorB2)
                   product1a = INTERPOLATE (color2, color5);
                else
                if (color4 == color2 && color3 == color2 && color1 != color5 && color2 != colorB0)
                   product1a = INTERPOLATE(color2, color5);
                else
                   product1a = color5;


            product1a = product1a | (product1b << 16);
            product2a = product2a | (product2b << 16);

                dP = product1a;
                //(dP + 1) = product1b;
                //(dP + dPitch) = product2a;
                //(dP + dPitch + 1) = product2b;

                bP ++;
                dP += 2;
            }//end of for ( finish= width etc..)

            srcPtr += srcPitch;
            deltaPtr += srcPitch << 1;
	}; //endof: for (height; height; height--)
    }
}
}
