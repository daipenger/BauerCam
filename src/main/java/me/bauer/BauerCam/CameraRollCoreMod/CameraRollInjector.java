package me.bauer.BauerCam.CameraRollCoreMod;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public final class CameraRollInjector implements IClassTransformer {

	private static final String deobfuscatedClass = "net.minecraft.client.renderer.EntityRenderer";

	private static final String deobfuscatedMethod = "orientCamera";
	private static final String obfuscatedMethod = "f";

	@Override
	public byte[] transform(final String obfuscated, final String deobfuscated, final byte[] bytes) {
		if (deobfuscatedClass.equals(deobfuscated)) {
			final ClassReader classReader = new ClassReader(bytes);
			final ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			final String method = obfuscated.equals(deobfuscated) ? deobfuscatedMethod : obfuscatedMethod;

			for (final MethodNode m : classNode.methods) {
				if (method.equals(m.name) && "(F)V".equals(m.desc)) {
					m.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/bauer/BauerCam/CameraRoll",
							"applyRoll", "()V", false));

					final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
					classNode.accept(classWriter);
					return classWriter.toByteArray();
				}
			}
		}

		return bytes;
	}

}
