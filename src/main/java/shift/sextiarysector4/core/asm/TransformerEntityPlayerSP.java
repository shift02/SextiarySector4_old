package shift.sextiarysector4.core.asm;

import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AnalyzerAdapter;

import com.google.common.collect.Sets;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class TransformerEntityPlayerSP implements IClassTransformer, Opcodes {
    
    /** 弄るクラス */
    private static final String TARGET_CLASS_NAME = "net.minecraft.client.entity.EntityPlayerSP";
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        try {
            final String targetClassName = TARGET_CLASS_NAME;
            if (targetClassName.equals(transformedName)) {
                System.out.println("start transform > EntityPlayerSP");
                ClassReader classReader = new ClassReader(bytes);
                ClassWriter classWriter = new ClassWriter(0);
                classReader.accept(new addHooksVisitor(name, classWriter), ClassReader.EXPAND_FRAMES);
                return classWriter.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("failed : EntityPlayerSP loading", e);
        }
        
        return bytes;
    }
    
    static class addHooksVisitor extends ClassVisitor {
        String owner;
        
        public addHooksVisitor(String owner, ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
            this.owner = owner;
        }
        
        /** 弄るメソッド */
        private final Set<String> targetMethodName = Sets.newHashSet("onLivingUpdate", "func_70636_d");
        private final String targetMethoddesc = "()V";
        
        private boolean isTransformed = false;
        
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            
            if (!isTransformed) {
                
                FMLDeobfuscatingRemapper remap = FMLDeobfuscatingRemapper.INSTANCE;
                
                if (targetMethodName.contains(remap.mapMethodName(owner, name, desc))
                        && targetMethoddesc.equals(remap.mapMethodDesc(desc))) {
                    mv = new AddHookMethodVisitor(owner, access, name, desc, mv);
                    
                    isTransformed = true;
                    
                }
                
            }
            
            return mv;
        }
    }
    
    static class AddHookMethodVisitor extends AnalyzerAdapter {
        
        protected AddHookMethodVisitor(String owner, int access, String name, String desc, MethodVisitor mv) {
            super(Opcodes.ASM5, owner, access, name, desc, mv);
        }
        
        private final String targetoOwner = "net/minecraft/util/FoodStats";
        private final Set<String> targetName = Sets.newHashSet("getFoodLevel", "func_75116_a");
        private final String targetDesc = "()I";
        
        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            
            FMLDeobfuscatingRemapper remap = FMLDeobfuscatingRemapper.INSTANCE;
            if (opcode == Opcodes.INVOKEVIRTUAL
                    && targetoOwner.equals(remap.map(owner))
                    && targetName.contains(remap.mapMethodName(owner, name, desc))
                    && targetDesc.equals(remap.mapMethodDesc(desc))) {
                
                //mv.visitVarInsn(ALOAD, 1);
                
                this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "shift/additionalstatus/asm/vanilla/EntityPlayerSPMethod",
                        "isSprinting", "(I)I", false);
                
            }
        }
    }
    
}
